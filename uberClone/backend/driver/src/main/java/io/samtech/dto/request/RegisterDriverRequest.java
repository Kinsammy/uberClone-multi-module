package io.samtech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("surname")
    private String familyName;
    private String middleName;
    @JsonProperty("firstName")
    private String givenName;
    private String phoneNumber;
    @JsonProperty("password")
    private String rawPassword;
    private MultipartFile licenseImage;
}
