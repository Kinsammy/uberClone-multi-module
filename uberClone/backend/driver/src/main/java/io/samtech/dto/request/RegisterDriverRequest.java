package io.samtech.dto.request;

import io.samtech.entity.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDriverRequest {
    private String email;

    private Integer gender;

    private int emailVerified;

    private String familyName;

    private String middleName;

    private String givenName;
    private String phoneNumber;
    private String rawPassword;

    @NotNull
    private Role role;
//    private MultipartFile licenseImage;
}
