package io.samtech.exception;
import io.samtech.exception.LogicException;
public final class UserVerifyCodeException {

    private UserVerifyCodeException() {
    }

    public static class Creation extends LogicException {
        public Creation() {
            super("app.user.verify-account.exception.verify-code.fail-make-code");
        }
    }

    public static class Verified extends LogicException {
        public Verified() {
            super("app.user.verify-account.exception.verify-code.verified");
        }
    }

    public static class Invalid extends LogicException {
        public Invalid() {
            super("app.user.verify-account.exception.verify-code.invalid");
        }
    }

}

