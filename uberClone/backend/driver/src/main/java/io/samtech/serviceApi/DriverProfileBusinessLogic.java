package io.samtech.serviceApi;

import io.samtech.dto.request.RegisterDriverRequest;

public interface DriverProfileBusinessLogic {
    void registerDriver(RegisterDriverRequest request);
}
