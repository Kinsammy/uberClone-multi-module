package io.samtech.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotNull
    private String token;
    @NotNull
    private String newPassword;
}
