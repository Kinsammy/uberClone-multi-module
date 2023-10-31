package io.samtech.controller;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterDriverRequest;
import io.samtech.serviceApi.DriverProfileServiceApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/driver")
@Tag(name = "API handler Driver flow")
public class DriverProfileController {
    private final DriverProfileServiceApi driverProfileServiceApi;

    @PostMapping(path = "/signup",  produces = "application/json")
    @Operation(summary = "Create Driver, only for Driver role")
    public Response<String> createNewPassenger(@Valid @RequestBody RegisterDriverRequest request){
        driverProfileServiceApi.registerDriver(request);
        return Response.ok();
    }
}
