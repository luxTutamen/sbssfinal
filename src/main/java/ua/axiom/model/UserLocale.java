package ua.axiom.model;

import java.util.Locale;

public enum UserLocale {
    ENG(Locale.ENGLISH),
    UKR(new Locale("UA"))
    ;

    public static final UserLocale DEFAULT_LOCALE = ENG;

    private Locale javaLocale;

    UserLocale(Locale javaLocale) {
        this.javaLocale = javaLocale;
    }

    public Locale toJavaLocale() {
        return javaLocale;
    }

}
