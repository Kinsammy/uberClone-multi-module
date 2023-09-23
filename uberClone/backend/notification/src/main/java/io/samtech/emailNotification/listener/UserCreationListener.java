package io.samtech.emailNotification.listener;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.samtech.application.event.event.UserCreationEvent;
import io.samtech.entity.EMail;
import io.samtech.entity.rdb.User;
import io.samtech.exception.UserVerifyCodeException;
import io.samtech.serviceApi.user.UserService;
import io.samtech.emailNotification.sendinBlueService.iEmailNotificationService;
import io.samtech.utils.DataProcessor;
import io.samtech.utils.DefaultInstance;
import io.samtech.utils.ExternalLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreationListener implements ApplicationListener<UserCreationEvent> {
    @Value(value = "${spring.application.secret-key}")
    private String appKey;

    private final MessageSource messages;
    private final iEmailNotificationService iEmailNotificationService;
    private final UserService userService;
    private final Environment environment;


    @Override
    public void onApplicationEvent(@NotNull UserCreationEvent event) {
        try {
            this.sendNotify(event);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendNotify(final UserCreationEvent event) throws UnirestException {
        log.info("sendNotify");
        EMail email = onSignUp(event.getUser());
        iEmailNotificationService.sendMail(email);
    }

    private EMail onSignUp(final User user) {
        HashMap<String, String> map = new HashMap<>();
        final String confirmationUrl =createEmailVerifyLink(user.getId());
        log.info("Verifying {}", confirmationUrl);
        map.put("name", user.getName());
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("verifylink", confirmationUrl);
        EMail email = EMail.builder()
                .receiver(Set.of(user.getEmail()))
                .sender("SamTech UberClone")
                .subject("Account Creation Email Verification")
                .templateName("AccountRegistrationEmailToken.ftlh")
                .templateParams(map)
                .templateId(3L)
                .bodyText("Registration Confirmation token is "+ confirmationUrl)
                .build();

        return email;
    }

    private String createEmailVerifyLink(Long userId) {
        try {
            String input = DefaultInstance.OBJECT_MAPPER.writeValueAsString(userId);
            String verifyCode = Base64.getEncoder().encodeToString(DataProcessor.encrypt(input, appKey));
            log.info("Verified {}", verifyCode);
            return ExternalLink.VERIFY_EMAIL_LINK + URLEncoder.encode(verifyCode, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IOException exception){
            log.error("Cannot create verify code", exception);
            throw new UserVerifyCodeException.Creation();
        }

    }
}
