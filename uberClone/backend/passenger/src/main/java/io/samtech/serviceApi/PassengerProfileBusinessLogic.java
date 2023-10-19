package io.samtech.serviceApi;

import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.NewPassengerRequest;

public interface PassengerProfileBusinessLogic {
    void registerPassenger(CreateUserRequest request);
}
