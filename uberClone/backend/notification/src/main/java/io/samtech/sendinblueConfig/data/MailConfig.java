package io.samtech.sendinblueConfig.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailConfig {
    private String apiKey;
    private String mailUrl;
}
