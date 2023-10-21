package io.samtech.sendinblueConfig.service;

import io.samtech.entity.EMail;

public interface IMailService {
    String sendMail(EMail eMail);
}
