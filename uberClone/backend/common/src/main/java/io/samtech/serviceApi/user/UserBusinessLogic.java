package io.samtech.serviceApi.user;

import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.entity.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserBusinessLogic {
    void createProfileTypeUser(User user);
    void verifyAccountWithToken(String token);
    void requestResetPassword(String email);
    void resetPassword(ResetPasswordRequest request);
    void uploadProfileImage(MultipartFile profileImage, Long userId);
}
