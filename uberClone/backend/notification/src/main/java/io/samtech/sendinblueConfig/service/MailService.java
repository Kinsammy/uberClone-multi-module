package io.samtech.sendinblueConfig.service;

import io.samtech.entity.EMail;
import io.samtech.sendinblueConfig.data.MailConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@AllArgsConstructor
@Slf4j
public class MailService implements IMailService{
    private final MailConfig mailConfig;
    @Override
    public String sendMail(EMail eMail) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailConfig.getApiKey());
        HttpEntity<EMail> requestEntity = new HttpEntity<>(eMail, headers);
        ResponseEntity<String> response =  restTemplate.postForEntity(mailConfig.getMailUrl(), requestEntity, String.class);
        return response.getBody();
    }
}
