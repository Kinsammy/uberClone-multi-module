package io.samtech.exception;

public class UserAlreadyExistByEmailException extends LogicException {

    public UserAlreadyExistByEmailException(){
        super("App.auth.exception.user-exist-by-email");
    }
}
