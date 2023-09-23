package io.samtech.emailNotification.sendinBlueService;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.samtech.emailNotification.dtos.request.SmsRequest;
import io.samtech.emailNotification.dtos.response.MailResponse;
import io.samtech.entity.EMail;
import sendinblue.ApiException;

import java.util.concurrent.CompletableFuture;

public interface iEmailNotificationService {
    CompletableFuture<MailResponse> sendMail(EMail eMail) throws UnirestException;

    CompletableFuture<MailResponse> sendSms(SmsRequest smsRequest) throws UnirestException, ApiException;


}
