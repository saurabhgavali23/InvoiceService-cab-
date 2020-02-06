package cabtestcases;

public class InvoiceSummary {


    public int numberOfRecords;
    public double totalFare;
    public double averageFare;
    private RideRepository rideRepository;

    public InvoiceSummary(int numberOfRides, double totalFare, double averageFare) {
        this.numberOfRecords = numberOfRides;
        this.totalFare = totalFare;
        this.averageFare = averageFare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceSummary that = (InvoiceSummary) o;
        return numberOfRecords == that.numberOfRecords &&
                Double.compare(that.totalFare, totalFare) == 0 &&
                Double.compare(that.averageFare, averageFare) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
