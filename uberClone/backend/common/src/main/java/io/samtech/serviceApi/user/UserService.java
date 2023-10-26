package io.samtech.serviceApi.user;

import io.samtech.dto.request.AuthenticationRequest;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService extends UserHandlerService, UserBusinessLogic {
    void creatUser(CreateUserRequest request);
    void registerUser(RegisterUserRequest request);
    void verifyCreatedUserEmail(String code);
    AuthenticationResponse login(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
