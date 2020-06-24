package ua.axiom.controller.error.exceptions;

import java.io.IOException;

public class LightException extends IOException {

    public LightException() {
        super();
    }

    public LightException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
