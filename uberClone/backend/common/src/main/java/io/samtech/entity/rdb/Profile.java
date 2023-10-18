package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

import static io.samtech.constants.CommonConstants.EntityName.PROFILE;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@Table(value = PROFILE)
@Entity
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private int isActivated;
    private int status;
    private Long userId;

    public Profile(String type){
        this.type = Objects.requireNonNullElse(type, CommonConstants.ProfileType.BASIC);
    }


}
