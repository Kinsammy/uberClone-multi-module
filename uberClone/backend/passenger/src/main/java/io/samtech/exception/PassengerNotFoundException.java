package io.samtech.exception;

public class PassengerNotFoundException extends LogicException {


    public PassengerNotFoundException() {
        super("app.passenger.exception.passsssenger-profile- id not found");
    }
}
