package ua.axiom.model;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum UserLocale {
    ENG(Locale.ENGLISH),
    UKR(new Locale("UA"))
    ;

    public static final UserLocale DEFAULT_LOCALE = ENG;

    private static final Map<Locale, UserLocale> localeToUserLocaleMap;

    static {
        localeToUserLocaleMap = new HashMap<>();
        for(UserLocale ulocale: UserLocale.values()) {
            localeToUserLocaleMap.put(ulocale.javaLocale, ulocale);
        }
    }

    private final Locale javaLocale;

    UserLocale(Locale javaLocale) {
        this.javaLocale = javaLocale;
    }

    public Locale toJavaLocale() {
        return javaLocale;
    }

    public static UserLocale toUserLocale(Locale locale) {
        return localeToUserLocaleMap.get(locale);
    }

    public static UserLocale getContextLocale() {
        return toUserLocale(LocaleContextHolder.getLocale());
    }


}
