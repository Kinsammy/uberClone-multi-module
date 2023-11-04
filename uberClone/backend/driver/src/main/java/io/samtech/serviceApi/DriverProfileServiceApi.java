package io.samtech.serviceApi;

import io.samtech.entity.Driver;

import java.util.List;

public interface DriverProfileServiceApi extends DriverProfileHandlerService, DriverProfileBusinessLogic{
    List<Driver>  findDriverByDrivingStatus();
}
