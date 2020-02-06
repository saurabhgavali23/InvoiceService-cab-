package cabtestcases;

import java.util.*;

public class RideRepository {

    Map<String, ArrayList<Ride>> userRide = new HashMap<>();

//    public RideRepository(Map<String[], ArrayList<Ride>> userRide) {
//        this.userRide = new HashMap<>();
//    }

    public void addRide(String userId, Ride[] rides) {
        ArrayList<Ride> rideArrayList = this.userRide.get(userId);
        if (rideArrayList == null) {
            this.userRide.put(userId, new ArrayList<>(Arrays.asList(rides)));
        }
    }

    public Ride[] getRide(String userId) throws InvoiceServiceException {
        try {
            return this.userRide.get(userId).toArray(new Ride[0]);
        } catch (NullPointerException n) {
            throw new InvoiceServiceException(n.getMessage(), InvoiceServiceException.ExceptionType.DATA_NOT_FOUND);
        }
    }
}
