package io.samtech.entity;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
public class Passenger extends Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder
    public Passenger(){
        super(CommonConstants.ProfileType.PASSENGER);
    }

}
