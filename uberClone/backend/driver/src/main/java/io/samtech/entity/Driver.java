package io.samtech.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.samtech.entity.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String licenseNumber;
    private String licenseImage;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Referee> referees;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BankInformation bankInformation;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonUnwrapped
    private User userDetails;
    private String drivingStatus;
}
