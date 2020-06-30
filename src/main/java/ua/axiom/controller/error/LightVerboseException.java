package ua.axiom.controller.error;

import java.io.IOException;

public class LightVerboseException extends IOException {
    private String simpleMessage;

    public LightVerboseException() { }

    public String getSimpleMessage() {
        return simpleMessage;
    }

    public void setSimpleMessage(String simpleMessage) {
        this.simpleMessage = simpleMessage;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
