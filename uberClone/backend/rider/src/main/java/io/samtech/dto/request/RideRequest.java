package io.samtech.dto.request;

import io.samtech.entity.Driver;
import io.samtech.entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private Long passengerId;
    private String origin;
    private String destination;
    private LocalDateTime pickUpTime;
    private LocalDateTime dropOffTime;
}
