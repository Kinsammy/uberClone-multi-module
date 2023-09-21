package io.samtech.exception;

public class UserAlreadyExitByPhoneNumberException extends LogicException {

    public UserAlreadyExitByPhoneNumberException(){
        super("App.auth.exception.user-exit-by-phoneNumber");
    }
}
