package io.samtech.serviceApi;

import io.samtech.entity.Driver;

public interface DriverProfileHandlerService {
    Driver saveDriverProfile(Driver driver);
    Driver findDriverProfileById(Long driverId);
    Iterable<Driver> getAllDriverProfiles();
    Driver updateDriverProfile(Driver driver);
    void deleteDriverProfile(Driver driver);
    void deleteDriverProfileById(Long driverId);
}
