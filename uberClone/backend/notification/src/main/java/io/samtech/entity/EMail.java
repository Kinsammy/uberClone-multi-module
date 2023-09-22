package io.samtech.entity;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class EMail {
    private Set<String> receiver;
    private Set<String> ccTo;
    private Set<String> bccTo;
    private String subject;
    private String sender;
    private List<File> attachments;
    private String templateName;
    private String bodyText;
    private Map<String, String> templateParams;
    private Long templateId;

    @Override
    public String toString() {
        return String.format("EmailContent{sendTo=%s, ccTo=%s, subject='%s', mailTemplate=%s}",
                receiver,
                ccTo,
                subject,
                templateName);
    }
}
