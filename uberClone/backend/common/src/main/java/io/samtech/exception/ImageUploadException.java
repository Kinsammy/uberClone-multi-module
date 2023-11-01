package io.samtech.exception;

import static io.samtech.configuration.message.Translator.eval;

public class ImageUploadException extends LogicException {
    public ImageUploadException(String message) {
        super(eval(message));
    }
}
