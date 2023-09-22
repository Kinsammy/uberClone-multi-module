package io.samtech.emailNotification.listener;

import io.samtech.application.event.event.UserCreationEvent;
import io.samtech.entity.EMail;
import io.samtech.entity.rdb.User;
import io.samtech.serviceApi.user.UserService;
import io.samtech.emailNotification.sendinBlueService.iEmailNotificationService;
//import io.samtech.utils.DefaultInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

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
        } finally {

        }

    }

    private void sendNotify(final UserCreationEvent event) {
        EMail email = onSignUp(event.getUser());
    }

    private EMail onSignUp(final User user) {
        HashMap<String, String> map = new HashMap<>();
        final String confirmationUrl =createEmailVerifyLink(user.getId());

        return null;
    }

    private String createEmailVerifyLink(Long userId) {
//        try {
//            String input = DefaultInstance
//        }
        return null;
    }
}
