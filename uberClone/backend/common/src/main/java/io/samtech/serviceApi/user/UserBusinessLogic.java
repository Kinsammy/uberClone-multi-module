package io.samtech.serviceApi.user;

import io.samtech.entity.models.User;

public interface UserBusinessLogic {
    void createProfileTypeUser(User user);
    void verifyAccountWithToken(String token);
}
