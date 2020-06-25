package ua.axiom.controller.error.exceptions;

import java.io.IOException;

public class LightVerboseException extends IOException {
    private String simpleMessage;

    public LightVerboseException(String message) {
        super("");
        simpleMessage = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getSimpleMessage() {
        return simpleMessage;
    }
}
