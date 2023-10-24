package io.samtech.serviceApi.user;

import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.entity.models.User;

public interface UserBusinessLogic {
    void createProfileTypeUser(User user);
    void verifyAccountWithToken(String token);
    void requestResetPassword(String email);
    void resetPassword(ResetPasswordRequest request);
}
