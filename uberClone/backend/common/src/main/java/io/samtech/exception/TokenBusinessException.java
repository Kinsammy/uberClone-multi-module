package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class TokenBusinessException extends LogicException {

    public TokenBusinessException(){
        super(eval("app.token-is-not-valid"));
    }
}
