package ua.axiom.service.misc;

public class MiscNulls {
    public static <T, E extends Throwable> T getOrThrow(T object, E ex) throws E {
        if(object == null) {
            throw ex;
        }
        return object;
    }
}
