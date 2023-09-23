package io.samtech.exception;

public final class UserVerifyCodeException {
    private UserVerifyCodeException() {

    }

    public static class Creation extends LogicException {
        public Creation() {
            super("app.user.verify-account.exception.verify-code.fail-make-code");
        }
    }
}
