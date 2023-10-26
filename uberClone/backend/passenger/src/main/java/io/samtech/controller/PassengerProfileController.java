package io.samtech.controller;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.NewPassengerRequest;
import io.samtech.entity.Passenger;
import io.samtech.serviceApi.PassengerProfileServiceApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/passenger")
@Tag(name = "API handler Passenger flow")
public class PassengerProfileController {
    private final PassengerProfileServiceApi passengerProfileServiceApi;
    @PostMapping(path = "/signup",  produces = "application/json")
    @Operation(summary = "Create Passenger, only for Passenger role")
    public Response createNewPassenger(@Valid @RequestBody CreateUserRequest request){
        passengerProfileServiceApi.registerPassenger(request);
        return Response.ok();
    }

    @GetMapping(path = "{passengerId}",  produces = "application/json")
    @Operation(summary = "find passenger endpoint, only for admin role")
    public Response findPassengerProfileById(@Valid @PathVariable Long passengerId){
        passengerProfileServiceApi.findPassengerProfileById(passengerId);
        return Response.ok();
    }

    @DeleteMapping(path = "/delete/{passengerId}",  produces = "application/json")
    @Operation(summary = "delete passenger endpoint, only for admin role")
    public Response deletePassengerProfileById(@Valid @PathVariable Long passengerId){
        passengerProfileServiceApi.deletePassengerProfileById(passengerId);
        return Response.ok();
    }

    @PutMapping(path = "/upadate-account", produces = "application/json")
    @Operation(summary = "update passenger account details endpoint, for all users")
    public ResponseEntity<?> updatePassenger(@Valid @RequestBody Passenger passenger){
        var response = passengerProfileServiceApi.updatePassengerProfile(passenger);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
