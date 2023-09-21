package io.samtech.exception;

public class RoleNotFoundException extends LogicException {

    public RoleNotFoundException(){
        super("app.role.exception.not-found");
    }
}
