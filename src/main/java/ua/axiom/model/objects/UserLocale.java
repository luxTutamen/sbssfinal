package ua.axiom.model.objects;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
