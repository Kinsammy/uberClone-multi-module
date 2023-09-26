package io.samtech.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DomainUserDetailsService extends UserDetailsService {

    SecuredUserDetails loadUserById(final Long userId);
    SecuredUserDetails loadUserByPreferredUsername(final String preferredUserName);
}
