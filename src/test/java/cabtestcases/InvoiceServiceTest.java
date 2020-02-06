package cabtestcases;

import invoiceservices.InvoiceServices;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {

    InvoiceServices invoiceServices = null;

    @Before
    public void setUp() {
        invoiceServices = new InvoiceServices(new RideRepository());
    }

    @Mock
    RideRepository rideRepositoryMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void givenDistanceAndTime_shouldReturnTotalFare() {

        double distance = 2.0;
        int time = 5;
        double totalFare = invoiceServices.calculateTotalFare(distance, time);
        Assert.assertEquals(25, totalFare, 0.0);
    }

    @Test
    public void givenDistanceAndTime_ShouldReturnMinimumFare() {

        double distance = 0.4;
        int time = 0;
        double totalFare = invoiceServices.calculateTotalFare(distance, time);
        Assert.assertTrue(5.0 == totalFare);
    }

    @Test
    public void givenMultipleDistanceAndTime_ShouldReturnInvoiceSummary() {

        Ride[] ride1 = {new Ride(2.0, 5),
                new Ride(0.1, 1)};

        InvoiceSummary invoiceSummary = invoiceServices.calculateTotalFare(ride1);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30, 15);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummary() throws InvoiceServiceException {

        String userId = "Ab.com";

        Ride[] ride1 = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };

        invoiceServices.addRide(userId, ride1);
        InvoiceSummary invoiceSummary = this.invoiceServices.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30, 15);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenWrongNumberOfRecords_ShouldNotEqual() {

        try {
            String userId = "Ab.com";

            Ride[] ride1 = {new Ride(2.0, 5),
                    new Ride(0.1, 1)
            };

            invoiceServices.addRide(userId, ride1);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(3, 30, 15);
            InvoiceSummary invoiceSummary = invoiceServices.getInvoiceSummary(userId);
            Assert.assertNotEquals(expectedInvoiceSummary, invoiceSummary);

        } catch (InvoiceServiceException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummaryUsingMockito() {

        try {
            String userId = "Ab.com";

            Ride[] ride1 = {new Ride(2.0, 5),
                    new Ride(0.1, 1)
            };

            InvoiceServices invoiceServices = new InvoiceServices(rideRepositoryMock);
            Mockito.doNothing().when(rideRepositoryMock).addRide(userId, ride1);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30, 15);

            when(rideRepositoryMock.getRide(userId)).thenReturn(ride1);
            InvoiceSummary invoiceSummary = invoiceServices.getInvoiceSummary(userId);
            Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
        } catch (InvoiceServiceException e) {
            //e.printStackTrace();
        }


    }

    @Test
    public void givenWrongUserId_ShouldThrowExceptionUsingMockito() {

        try {
            String userId = "Ab.com";

            Ride[] ride1 = {new Ride(2.0, 5),
                    new Ride(0.1, 0)
            };

            InvoiceServices invoiceServices = new InvoiceServices(rideRepositoryMock);
            Mockito.doNothing().when(rideRepositoryMock).addRide(userId, ride1);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30, 15);

            when(rideRepositoryMock.getRide("userId.com")).thenThrow(new NullPointerException("ID Not Available"));
            InvoiceSummary invoiceSummary = invoiceServices.getInvoiceSummary("userId.com");
            Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);

        } catch (InvoiceServiceException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void givenNullRecords_ShouldThrowExceptionUsingMockito() {

        try {

            InvoiceServices invoiceServices = new InvoiceServices(rideRepositoryMock);
            Mockito.doNothing().when(rideRepositoryMock).addRide(null, null);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30, 15);

            when(rideRepositoryMock.getRide(null)).thenThrow(new NullPointerException("Value Is Null"));
            InvoiceSummary invoiceSummary = invoiceServices.getInvoiceSummary(null);
            Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);

        } catch (InvoiceServiceException e) {
            e.printStackTrace();
        }
    }
}
