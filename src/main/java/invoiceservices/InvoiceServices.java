package invoiceservices;

import cabtestcases.InvoiceServiceException;
import cabtestcases.InvoiceSummary;
import cabtestcases.Ride;
import cabtestcases.RideRepository;

public class InvoiceServices {

    private static final int MINIMUM_COST_PER_KILOMETER = 10;
    private static final double COST_PER_TIME = 1;
    private RideRepository rideRepository;
    private double MINIMUM_FARE = 5.0;

    public InvoiceServices(RideRepository rideRepositoryMock) {
        //this.rideRepository = new RideRepository();
        this.rideRepository = rideRepositoryMock;
    }


    public double calculateTotalFare(double distance, int time) {

        double totalFare = distance * MINIMUM_COST_PER_KILOMETER + time * COST_PER_TIME;

        return Math.max(totalFare, MINIMUM_FARE);
    }

    public InvoiceSummary calculateTotalFare(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride : rides)
            totalFare = totalFare + this.calculateTotalFare(ride.distance, ride.time);
        return new InvoiceSummary(rides.length, totalFare, totalFare / rides.length);
    }

    public void addRide(String userId, Ride[] ride1) {
        rideRepository.addRide(userId, ride1);
    }

    public InvoiceSummary getInvoiceSummary(String userId) throws InvoiceServiceException {
        try {
            return this.calculateTotalFare(rideRepository.getRide(userId));
        } catch (NullPointerException n) {
            throw new InvoiceServiceException(n.getMessage(), InvoiceServiceException.ExceptionType.DATA_NOT_FOUND);
        }
    }
}
