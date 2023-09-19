package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

import static io.samtech.constants.CommonConstants.EntityName.PROFILE;

@Getter
@Setter
@ToString
@Table(value = PROFILE)
public class Profile extends AbstractJdbcEntity<Long>{
    private String type;
    private int isActive;
    private int status;
    private Long userId;

    public Profile(String type){
        this.type = Objects.requireNonNullElse(type, CommonConstants.ProfileType.BASIC);
    }
}
