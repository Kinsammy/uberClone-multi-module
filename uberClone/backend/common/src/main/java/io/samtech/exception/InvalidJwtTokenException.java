package io.samtech.exception;

import org.springframework.security.core.AuthenticationException;

import static io.samtech.configuration.message.Translator.eval;

public class InvalidJwtTokenException  extends AuthenticationException {

    public InvalidJwtTokenException(){
        super(eval("app.auth.exception.token-not-valid"));
    }
}
