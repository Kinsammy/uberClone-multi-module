package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class DriverNotFoundException extends LogicException{

    public DriverNotFoundException(){
        super(eval("app.exception.driver-profile not found"));
    }
}
