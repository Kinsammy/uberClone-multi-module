package io.samtech.serviceApi.user;

import io.samtech.entity.models.User;

public interface UserHandlerService {
    User findActiveUserById(final Long id);
    User findActiveUserByPreferredUsername(final String preferredUsername);
    User findUserByUsername(final String username);
    User findUserByEmail(final String email);
    User findUserByPhoneNumber(final String phoneNumber);

    boolean existsByEmail(final String email);
    boolean existsByUsername(final String username);
    boolean existsByPhoneNumber(final String phoneNumber);
    User saveUser(final User user);
}
