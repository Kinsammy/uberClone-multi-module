package io.samtech.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.samtech.entity.models.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JsonUnwrapped
    private User userDetails;

}
