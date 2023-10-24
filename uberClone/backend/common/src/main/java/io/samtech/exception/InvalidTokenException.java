package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class InvalidTokenException extends LogicException {

    public InvalidTokenException(String message) {
        super(eval(message));
    }
}
