package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class PassengerNotFoundException extends LogicException {


    public PassengerNotFoundException() {
        super(eval("app.exception.passenger-profile not found"));
    }
}
