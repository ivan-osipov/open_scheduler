package org.pyjjs.scheduler.core.common.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LocaleResolver {
    private static final Logger LOG = LoggerFactory.getLogger(LocaleResolver.class);
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
        try {
            return String.format(new String(rawString.getBytes("ISO-8859-1"), "UTF-8"), params);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("Encoding is incorrect",e);
        }
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
