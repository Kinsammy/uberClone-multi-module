package io.samtech.emailNotification.sendinBlueService;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.samtech.emailNotification.dtos.request.SmsRequest;
import io.samtech.emailNotification.dtos.response.MailResponse;
import io.samtech.entity.EMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibApi.TransactionalSmsApi;
import sibModel.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SendInBlueService implements iEmailNotificationService {
    final ApiClient defaultClient = Configuration.getDefaultApiClient();

    @Value(value = "${sendinblue.api.key}")
    String PRIVATE_KEY;
    @Value(value = "${spring_mail_sender}")
    String SENDER;
    @Override
    @Async
    public CompletableFuture<MailResponse> sendMail(EMail eMail) throws UnirestException {
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(PRIVATE_KEY);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(new SendSmtpEmailSender().email(SENDER));
        String sendTo = eMail.getReceiver().toString();
        sendTo = sendTo.substring(1, sendTo.length() -1);
        sendSmtpEmail.setTo(List.of(new SendSmtpEmailTo().email(sendTo)));
        sendSmtpEmail.setSubject(eMail.getSubject());
//    sendSmtpEmail.setTextContent("testing from local server");
        sendSmtpEmail.setParams(eMail.getTemplateParams());
        sendSmtpEmail.setTemplateId(eMail.getTemplateId());

        // SendSmtpEmail | Values to send a transactional email
        try {
            CreateSmtpEmail result = apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TransactionalEmailsApi#sendTransacEmail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CompletableFuture<MailResponse> sendSms(SmsRequest smsRequest) throws UnirestException, ApiException {
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(PRIVATE_KEY);

        TransactionalSmsApi apiInstance = new TransactionalSmsApi();
        SendTransacSms sendTransacSms = new SendTransacSms(); // SendTransacSms | Values to send a transactional SMS


        SendSms res = apiInstance.sendTransacSms(sendTransacSms);

        log.info(res.getReference());
        log.info(res.toString());
        try {
            SendSms result = apiInstance.sendTransacSms(sendTransacSms);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TransactionalSmsApi#sendTransacSms");
            e.printStackTrace();
        }
        return null;
    }
}
