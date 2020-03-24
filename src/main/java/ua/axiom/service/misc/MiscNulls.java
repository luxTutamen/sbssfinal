package ua.axiom.service.misc;

public class MiscNulls {
    public static <T> T getOrThrow(T object, RuntimeException ex) throws RuntimeException {
        if(object == null) {
            throw ex;
        }
        return object;
    }
}
