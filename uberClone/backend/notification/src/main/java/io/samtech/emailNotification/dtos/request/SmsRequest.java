package io.samtech.emailNotification.dtos.request;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsRequest {
    private String receiver;
    private String Sender;
    private String smsContent;
    private String templateName;
    private String templateId;
    private Map<String, String> templateParams;
}
