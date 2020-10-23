package ua.axiom.controller.error;

public class LightweightException extends RuntimeException {
    @Override
    public synchronized Throwable fillInStackTrace() { return this; }
}
