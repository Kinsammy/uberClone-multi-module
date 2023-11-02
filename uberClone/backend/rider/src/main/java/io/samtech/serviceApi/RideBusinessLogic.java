package io.samtech.serviceApi;

import io.samtech.dto.RideResponse;
import io.samtech.dto.request.RideRequest;

public interface RideBusinessLogic {
    RideResponse bookRide(RideRequest request);

}
