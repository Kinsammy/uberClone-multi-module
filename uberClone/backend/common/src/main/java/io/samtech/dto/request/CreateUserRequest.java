package io.samtech.dto.request;

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

    private String familyName;

    private String middleName;

    private String givenName;
    private String phoneNumber;

//    @NotNull
    private List<Long> roleIds;


}
