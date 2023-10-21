package io.samtech.sendinblueConfig.config;

import io.samtech.sendinblueConfig.data.MailConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendinblueConfig {
    @Value("${sendinblue.mail.url}")
    private String mailUrl;
    @Value("${sendinblue.api.key}")
    private String mailApiKey;


    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey, mailUrl);
    }
}
