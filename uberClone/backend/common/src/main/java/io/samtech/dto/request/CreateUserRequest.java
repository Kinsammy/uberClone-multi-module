package io.samtech.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateUserRequest {
    private String email;

    private Integer gender;

    private int emailVerified;

    private String familyName;

    private String middleName;

    private String givenName;
    private String phoneNumber;

    @NotNull
    private List<Long> roleIds;

    public CreateUserRequest(String email, Integer gender, int emailVerified, String familyName, String middleName, String givenName, String phoneNumber, List<Long> roleIds) {
        this.email = email;
        this.gender = gender;
        this.emailVerified = emailVerified;
        this.familyName = familyName;
        this.middleName = middleName;
        this.givenName = givenName;
        this.phoneNumber = phoneNumber;
        this.roleIds = roleIds;
    }
}
