package io.samtech.security.service.auth;

import io.samtech.configuration.annotation.validator.PhoneNumberValidator;
import io.samtech.entity.models.User;
import io.samtech.security.DomainUserDetailsService;
import io.samtech.security.SecuredUser;
import io.samtech.security.SecuredUserDetails;
import io.samtech.serviceApi.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class DaoUserDetailsService implements DomainUserDetailsService {
    private static final EmailValidator emailValidator = new EmailValidator();
    private static final PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
    private final UserService userService;

    public DaoUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SecuredUserDetails loadUserById(Long userId) {
        User user = userService.findActiveUserById(userId);
        return new SecuredUserDetails((SecuredUser) user, userId.toString());
    }

    @Override
    public SecuredUserDetails loadUserByPreferredUsername(String preferredUserName) {
        User user = userService.findActiveUserByPreferredUsername(preferredUserName);
        return new SecuredUserDetails((SecuredUser) user, preferredUserName);
    }

    @Override
    public SecuredUserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        log.debug("authenticating {}", principal);
        final String lowercasePrincipal = principal.toLowerCase(Locale.ENGLISH);
        User user;
        AuthenticationType authenticationType;

        switch (determineAuthenticationType(principal)) {
            case EMAIL -> {
                user = userService.findUserByEmail(lowercasePrincipal);
                authenticationType = AuthenticationType.EMAIL;
            }
            case PHONE_NUMBER -> {
                user = userService.findUserByPhoneNumber(principal);
                authenticationType = AuthenticationType.EMAIL;
            }
            default -> {
                user = userService.findUserByUsername(lowercasePrincipal);
                authenticationType = AuthenticationType.EMAIL;
            }

        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found with provided principal");
        }

        return new SecuredUserDetails((SecuredUser) user, lowercasePrincipal);
    }

    private AuthenticationType determineAuthenticationType(String principal) {
        if (emailValidator.isValid(principal, null)) {
            return AuthenticationType.EMAIL;
        } else if (phoneNumberValidator.isValid(principal, null)) {
            return AuthenticationType.PHONE_NUMBER;
        } else {
            return AuthenticationType.USERNAME;
        }
    }

    enum AuthenticationType {
        EMAIL,
        PHONE_NUMBER,
        USERNAME
    }
}
