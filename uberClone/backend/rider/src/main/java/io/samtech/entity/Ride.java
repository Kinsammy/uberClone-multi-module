package io.samtech.entity;

import io.samtech.entity.models.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name ="passenger_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Passenger passenger;
    @JoinColumn(name ="driver_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Driver driver;
    private String origin;
    private String destination;
    private LocalDateTime pickUpTime;
    private LocalDateTime dropOffTime;
}
