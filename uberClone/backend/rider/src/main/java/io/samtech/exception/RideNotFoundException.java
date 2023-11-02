package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class RideNotFoundException extends LogicException {

    public RideNotFoundException(){
        super(eval("app.ride.not-found"));
    }
}
