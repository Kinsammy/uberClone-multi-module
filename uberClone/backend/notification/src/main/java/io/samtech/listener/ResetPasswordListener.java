package io.samtech.listener;

import io.samtech.application.event.event.ResetPasswordEvent;
import io.samtech.entity.EMail;
import io.samtech.entity.Recipient;
import io.samtech.entity.Sender;
import io.samtech.entity.models.User;
import io.samtech.sendinblueConfig.service.MailService;
import io.samtech.serviceApi.token.ITokenService;
import io.samtech.serviceApi.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResetPasswordListener implements ApplicationListener<ResetPasswordEvent> {
    private final UserService userService;
    private final ITokenService tokenService;
    private final MailService mailService;
    @Value(value = "${spring_mail_sender}")
    private String sender;

    @Override
    public void onApplicationEvent(@NotNull ResetPasswordEvent event) {
        try {
            sendResetPasswordNotify(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResetPasswordNotify(ResetPasswordEvent event) throws IOException {
        String token = tokenService.generateAndSaveToken(event.getUser());
        log.info(token);

        EMail email = onResetPassword(event.getUser(), token);
        mailService.sendMail(email);
    }

    private EMail onResetPassword(User user, String token) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("Reset Password Token", token);
        String emailTemplateContent = loadEmailTemplateContent("AccountRegistrationEmailToken.ftlh");

        EMail email = new EMail();
        email.getTo().add(new Recipient(
                user.getName(),
                user.getEmail()
        ));
        email.setSender(new Sender("UberClone", sender));
        email.setSubject("Reset Password");
        email.setHtmlContent(emailTemplateContent); // Set the template content
        email.setTemplateParams(map);

        return email;
    }

    // Method to load the content of an email template file
    private String loadEmailTemplateContent(String templateFileName) throws IOException {
        // Load the content of the template from the file
        // You may need to adjust this code based on how your templates are stored
        // This is a simplified example assuming the templates are in a folder named "templates"
        String templateFilePath = "templates/" + templateFileName;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templateFilePath);
        if (inputStream != null) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } else {
            throw new IOException("Email template file not found: " + templateFileName);
        }
    }
}
