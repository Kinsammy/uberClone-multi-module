package io.samtech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegisterUserRequest {
    private Integer gender;

    @NotNull
    private String familyName;

    private String middleName;

    @NotNull
    private String givenName;

    @NotNull

    private String email;

    private String phoneNumber;

    @NotNull
    @JsonProperty(value = "password")
    private String rawPassword;
}
