package io.samtech.controller;

import io.samtech.configuration.response.Response;
import io.samtech.dto.RideResponse;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RideRequest;
import io.samtech.serviceApi.RideServiceApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ride")
@Tag(name = "API handler for Ride flow")
public class RideController {
    private final RideServiceApi rideServiceApi;

    @PostMapping(path = "/signup",  produces = "application/json")
    @Operation(summary = "Passenger book ride, only for Passenger role")
    public ResponseEntity<RideResponse> bookRide(@Valid @RequestBody RideRequest request){
        var response = rideServiceApi.bookRide(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
