package ua.axiom.service;

import com.mysql.cj.exceptions.WrongArgumentException;
import org.springframework.stereotype.Service;
import ua.axiom.service.misc.MiscNulls;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Meant to provide localised text for html pages
 */
@Service
public class LocalisationService {
    private static Map<Locale, Map<String, String>> localeToDictionaryMap;

    public LocalisationService() {
        localeToDictionaryMap = new HashMap<>();
        try {
            loadProperties("src/main/resources/properties/pagecontent_eng.properties", Locale.ENGLISH);
            loadProperties("src/main/resources/properties/pagecontent_ukr.properties", new Locale("UA"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }
    }

    private String getLocalisedMessage(String messageKey, Locale locale) {
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

    public void setLocalisedMessages(Map<String, Object> model, Locale locale, String... messages) {
        for (String s : messages) {
            model.put(s, getLocalisedMessage(s, locale));
        }
    }

    private Map<String, String> getDictionary(Locale locale) {
        Map<String, String> dictionary = localeToDictionaryMap.get(locale);

        return MiscNulls.getOrThrow(dictionary, new IllegalStateException("Locale " + locale + " is not present in " + this.getClass()));
    }

    private void addLocale(Locale locale) {
        if(localeToDictionaryMap.containsKey(locale)) {
            throw new WrongArgumentException("Locale " + locale + "is already present in " + this.getClass());
        }

        localeToDictionaryMap.put(locale, new HashMap<>());
    }

    private void loadProperties(String filePath, Locale toLoad) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

        Enumeration<Object> keys = properties.keys();
        if(!localeToDictionaryMap.containsKey(toLoad)) {
            addLocale(toLoad);
        }

        Map<String, String> localDictionary = localeToDictionaryMap.get(toLoad);
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            localDictionary.put(key, properties.getProperty(key));
        }
    }
}
