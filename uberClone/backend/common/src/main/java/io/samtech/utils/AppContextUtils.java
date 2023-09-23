package io.samtech.utils;

import io.samtech.security.SecuredUser;
import io.samtech.security.SecuredUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AppContextUtils {

    private static ApplicationContext applicationContext;

    @Autowired
    public AppContextUtils(ApplicationContext applicationContext) {
        AppContextUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Optional<SecuredUser> getCurrentLoginUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(SecuredUserDetails.class::cast)
                .map(SecuredUserDetails::getUser);
    }

    public static Long getCurrentLoginUserId() {
        return getCurrentLoginUser().map(SecuredUser::getId).orElse(CommonConstants.User.SYSTEM_ID);
    }

}
