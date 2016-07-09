package org.pyjjs.scheduler.core.actors.common.locale;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LocaleResolver {
    private static ConcurrentMap<String, LocaleResolver> instance = new ConcurrentHashMap<>();

    private Properties properties;

    private LocaleResolver(LangResolver language) throws IOException {
        properties = new Properties();
        String propFileName = language.getFileName();
        properties.load(getClass().getClassLoader().getResourceAsStream(propFileName));
    }

    public Properties values() {
        return properties;
    }

    public String getString(String key, Object... params) {
        String rawString = properties.getProperty(key);
        return String.format(rawString, params);
    }

    public static LocaleResolver get(LangResolver langResolver) throws LocaleNotFoundException {
        LocaleResolver localeResolver = instance.get(langResolver.getShortLang());
        if(localeResolver == null) {
            synchronized (LocaleResolver.class) {
                localeResolver = instance.get(langResolver.getShortLang());
                if(localeResolver == null) {
                    try {
                        localeResolver = new LocaleResolver(langResolver);
                    } catch (IOException e) {
                        throw new LocaleNotFoundException(e.getMessage());
                    }
                    instance.put(langResolver.getShortLang(), localeResolver);
                }
            }
        }
        return localeResolver;
    }
}
