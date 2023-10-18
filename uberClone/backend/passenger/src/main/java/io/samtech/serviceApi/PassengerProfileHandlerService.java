package io.samtech.serviceApi;

import io.samtech.entity.Passenger;

public interface PassengerProfileHandlerService {
    Passenger savePassengerProfile(Passenger passenger);
    Passenger findPassengerProfileById(Long passengerProfileId);
    Iterable<Passenger> getAllPassengerProfiles();
    Passenger updatePassengerProfile(Passenger passenger);
    void deletePassengerProfile(Passenger passenger);
    void deletePassengerProfileById(Long passengerProfileId);
}
