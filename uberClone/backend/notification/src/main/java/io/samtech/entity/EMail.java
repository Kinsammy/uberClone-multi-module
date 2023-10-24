package io.samtech.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EMail {
    private Set<Recipient> to;
    private Set<String> ccTo;
    private Set<String> bccTo;
    private String subject;
    private Sender sender;
    private List<File> attachments;
    private String htmlContent;
    private Map<String, String> templateParams;
    private Long templateId;

    @Override
    public String toString() {
        return String.format("EmailContent{sendTo=%s, ccTo=%s, subject='%s', mailTemplate=%s}",
                to,
                ccTo,
                subject
        );
    }

    public void setReceiver(Set<Recipient> receiver) {
        this.to = receiver;
    }

    public Set<Recipient> getTo() {
        if (to == null) {
            to = new HashSet<>();
        }
        return to;
    }



}
