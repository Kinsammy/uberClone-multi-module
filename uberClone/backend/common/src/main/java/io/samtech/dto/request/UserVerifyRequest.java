package io.samtech.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserVerifyRequest {
    @NotNull
    private String token;
}
