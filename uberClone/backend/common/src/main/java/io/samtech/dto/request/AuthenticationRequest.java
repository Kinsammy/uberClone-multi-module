package io.samtech.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AuthenticationRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
