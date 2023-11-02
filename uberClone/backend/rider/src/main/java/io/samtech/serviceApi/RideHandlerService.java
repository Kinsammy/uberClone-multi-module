package io.samtech.serviceApi;

import io.samtech.entity.Ride;

public interface RideHandlerService {
    Ride saveRide(Ride ride);
    Ride findRideById(Long rideId);
    Iterable<Ride> getAllRides();
    Ride updateRide(Ride ride);
    void deleteRide(Ride ride);
    void deleteRideById(Long rideId);
}
