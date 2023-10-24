package io.samtech.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static io.samtech.configuration.message.Translator.eval;

public class UserNotExistedException extends UsernameNotFoundException {

    public UserNotExistedException(){
        super(eval("app.user.exception.not-found"));
    }
}
