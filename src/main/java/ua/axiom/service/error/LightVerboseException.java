package ua.axiom.service.error;

import java.io.IOException;

public abstract class LightVerboseException extends IOException {

    public abstract String getViewMessageName();

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
