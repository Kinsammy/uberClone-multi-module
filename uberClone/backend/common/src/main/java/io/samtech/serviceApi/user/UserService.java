package io.samtech.serviceApi.user;

import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.ResetPasswordResponse;

public interface UserService extends UserHandlerService, UserBusinessLogic {
    void creatUser(CreateUserRequest request);
    void registerUser(RegisterUserRequest request);
    void verifyCreatedUserEmail(String code);
    void sendResetPasswordToEmail(String email);
    ResetPasswordResponse resetPassword(ResetPasswordRequest request);
}
