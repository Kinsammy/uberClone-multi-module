package io.samtech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class NewPassengerRequest {

//
//    @NotNull
    private String familyName;

//    @NotNull
    private String givenName;

//
//    @NotNull
//  @ValidEmail
    private String email;

    //  @ValidPhoneNumber
    private String phoneNumber;
//
//    @NotNull
    @JsonProperty(value = "password")
//  @ValidPassword
    private String rawPassword;

}
