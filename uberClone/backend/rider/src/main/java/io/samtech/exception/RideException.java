package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class RideException extends LogicException{

    public RideException(String message){
        super(eval(message));
    }
}
