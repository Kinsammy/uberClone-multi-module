package io.samtech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.samtech.entity.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String email;

    private Integer gender;

    private int emailVerified;
    @JsonProperty("surname")
    private String familyName;

    private String middleName;

    private String givenName;
    private String phoneNumber;
    @JsonProperty("password")
    private String rawPassword;

    @NotNull
    private Role role;


}
