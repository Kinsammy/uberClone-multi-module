package io.samtech.serviceApi;

import io.samtech.entity.Driver;
import io.samtech.entity.models.Address;

import java.util.List;

public interface DriverProfileServiceApi extends DriverProfileHandlerService, DriverProfileBusinessLogic{
    List<Driver>  findDriverByDrivingStatus();

    Driver selectDriver(Address passengerLocation);
}
