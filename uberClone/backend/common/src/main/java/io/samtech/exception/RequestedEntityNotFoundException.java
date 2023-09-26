package io.samtech.exception;

import org.springframework.security.core.AuthenticationException;

import static io.samtech.configuration.message.Translator.eval;

public class RequestedEntityNotFoundException extends AuthenticationException {
    public RequestedEntityNotFoundException(){
        super(eval("app.security.exception.requested-entity-not-found"));
    }
}
