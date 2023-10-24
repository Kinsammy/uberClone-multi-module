package io.samtech.listener;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.samtech.application.event.event.UserCreationEvent;
import io.samtech.entity.EMail;
import io.samtech.entity.Recipient;
import io.samtech.entity.Sender;
import io.samtech.entity.models.User;
import io.samtech.exception.UserVerifyCodeException;
import io.samtech.sendinblueConfig.service.IMailService;
import io.samtech.serviceApi.token.ITokenService;
import io.samtech.utils.DataProcessor;
import io.samtech.utils.DefaultInstance;
import io.samtech.utils.ExternalLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreationListener implements ApplicationListener<UserCreationEvent> {
    @Value(value = "${spring_mail_sender}")
    private String sender;
//
//    private final MessageSource messages;
//    private final iEmailNotificationService iEmailNotificationService;
    private final ITokenService tokenService;
    private final IMailService mailService;


    @Override
    public void onApplicationEvent(@NotNull UserCreationEvent event) {
        try {
            this.sendNotify(event);
        } catch (UnirestException | MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendNotify(final UserCreationEvent event) throws UnirestException, MessagingException, IOException {
        log.info("sendNotify");
        String token = tokenService.generateAndSaveToken(event.getUser());
        log.info(token);
        EMail email = new EMail();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", event.getUser().getName());
        map.put("token", token);


        // Load the content of the email template
        String emailTemplateContent = loadEmailTemplateContent("AccountRegistrationEmailToken.ftlh");
        emailTemplateContent = loadContents(emailTemplateContent, map);
        email.getTo().add(new Recipient(
                event.getUser().getName(),
                event.getUser().getEmail()
        ));
        email.setSender(new Sender("UberClone", sender));
        email.setSubject("Account Creation Email Verification");
        email.setHtmlContent(emailTemplateContent); // Set the template content
        email.setTemplateParams(map);

        mailService.sendMail(email);
    }

    private String loadContents(String templateContent, HashMap<String, String> contents) {
        for (Map.Entry<String, String> entry : contents.entrySet()) {
            String placeHolder = "${" + entry.getKey() + "}";
            String value = entry.getValue();
            templateContent = templateContent.replace(placeHolder, value);
        }
        return templateContent;
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

//    private EMail onSignUp(final User user, String token) throws MessagingException, IOException {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("name", user.getName());
//        map.put("token", token);
////        map.put("username", user.getUsername());
////        map.put("password", user.getPassword());
////        map.put("verifylink", confirmationUrl);
////        EMail email = EMail.builder()
//////                .to((user.getName(), user.getEmail()))
////                .sender(new Sender("UberClone", sender))
////                .subject("Account Creation Email Verification")
////                .templateName("AccountRegistrationEmailToken.ftlh")
////                .templateParams(map)
////                .templateId(4L)
////                .htmlContent("Registration Confirmation token is " + confirmationUrl)
////                .build();
//
//        EMail email = new EMail();
//        email.getTo().add(new Recipient(
//                user.getName(), user.getEmail()));
//        email.setSender(new Sender("UberClone", sender));
//        email.setSubject("Account Creation Email Verification");
//        email.setTemplateName("AccountRegistrationEmailToken.ftlh");
//        email.setTemplateParams(map);
//        email.setHtmlContent("Registration Confirmation token is " );
//
//
//        return email;
//    }

//    private String createEmailVerifyLink(Long userId) {
//        try {
//            String input = DefaultInstance.OBJECT_MAPPER.writeValueAsString(userId);
//            String verifyCode = Base64.getEncoder().encodeToString(DataProcessor.encrypt(input, appKey));
//            log.info("Verified {}", verifyCode);
//            return ExternalLink.VERIFY_EMAIL_LINK + URLEncoder.encode(verifyCode, StandardCharsets.UTF_8);
//        } catch (GeneralSecurityException | IOException exception){
//            log.error("Cannot create verify code", exception);
//            throw new UserVerifyCodeException.Creation();
//        }
//
//    }
}
