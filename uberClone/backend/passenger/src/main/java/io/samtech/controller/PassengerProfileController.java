package io.samtech.controller;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.NewPassengerRequest;
import io.samtech.serviceApi.PassengerProfileServiceApi;
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
@RequestMapping(path = "/passenger")
//@Tag(name = "API handler Passenger flow")
public class PassengerProfileController {
    private final PassengerProfileServiceApi passengerProfileServiceApi;
    @PostMapping(path = "/signup",  produces = "application/json")
    @Operation(summary = "Create Customer user, only for admin role")
    public Response createNewPassenger(@Valid @RequestBody CreateUserRequest request){
        passengerProfileServiceApi.registerPassenger(request);
        return Response.ok();
    }
}
