package ua.axiom.model;

import java.util.Locale;

public enum UserLocale {
    ENG(Locale.ENGLISH, "en"),
    UKR(new Locale("UA"), "ua")
    ;

    public static final UserLocale DEFAULT_LOCALE = ENG;

    private Locale javaLocale;

    private String urlString;

    UserLocale(Locale javaLocale, String urlString) {
        this.javaLocale = javaLocale;
        this.urlString = urlString;
    }

    public String toUrlString() {
        return urlString;
    }

    public Locale toJavaLocale() {
        return javaLocale;
    }

}
