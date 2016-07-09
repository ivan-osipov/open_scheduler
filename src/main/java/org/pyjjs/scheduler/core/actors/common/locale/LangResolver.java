package org.pyjjs.scheduler.core.actors.common.locale;

public class LangResolver {

    private static final String LOCALE_FILE_NAME = "locale_%s.properties";
    private String fileName;
    private String shortLang;


    public LangResolver(String shortLang) {
        fileName = String.format(LOCALE_FILE_NAME, shortLang);
        this.shortLang = shortLang;
    }

    public String getFileName() {
        return fileName;
    }

    public String getShortLang() {
        return shortLang;
    }
}
