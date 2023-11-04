package io.samtech.dto.request;

import io.samtech.entity.Driver;
import io.samtech.entity.Passenger;
import io.samtech.entity.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private Long passengerId;
    private Address origin;
    private Address destination;
    private LocalDateTime pickUpTime;
    private LocalDateTime dropOffTime;
}
