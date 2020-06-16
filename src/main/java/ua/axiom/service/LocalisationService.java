package ua.axiom.service;

import org.springframework.stereotype.Service;
import ua.axiom.model.UserLocale;
import ua.axiom.service.misc.MiscNulls;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Provides localised text for html pages, localised regexe's for input check
 */
@Service
public class LocalisationService {
    private static Map<UserLocale, Map<String, String>> localeToDictionaryMap;

    public LocalisationService() {
        localeToDictionaryMap = new HashMap<>();
        try {
            loadProperties("src/main/resources/properties/pagecontent_eng.properties", UserLocale.ENG);
            loadProperties("src/main/resources/properties/pagecontent_ukr.properties", UserLocale.UKR);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public String getLocalisedMessage(String messageKey, UserLocale locale) {
        Map<String, String> dictionary = localeToDictionaryMap.get(locale);

        if(dictionary == null) {
            throw new IllegalStateException("Locale <" + locale + "> not present in the " + this.getClass());
        }

        String result = dictionary.get(messageKey);

        if(result == null) {
            return "Cannot find entry for key " + messageKey + ", for locale " + locale;
        } else {
            return result;
        }
    }

    public String getRegex(String key, UserLocale locale) {
        return localeToDictionaryMap.get(locale).get("regex." + key);
    }

    public void setLocalisedMessages(Map<String, Object> model, UserLocale locale, String... messages) {
        Arrays
                .stream(messages)
                .forEach(m -> model.put(m, getLocalisedMessage(m, locale)));
    }

    private void assureLocaleExistance(UserLocale locale) {
        if(localeToDictionaryMap.containsKey(locale)) {
            throw new IllegalArgumentException("Locale " + locale + "is already present in " + this.getClass());
        }

        localeToDictionaryMap.put(locale, new HashMap<>());
    }

    private void loadProperties(String filePath, UserLocale toLoad) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        assureLocaleExistance(toLoad);

        Enumeration<Object> keys = properties.keys();
        Map<String, String> localDictionary = localeToDictionaryMap.get(toLoad);
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            localDictionary.put(key, properties.getProperty(key));
        }
    }
}
