package org.pyjjs.scheduler.core.actors.common.locale;

public class LocaleNotFoundException extends Exception {
    private String reason;
    public LocaleNotFoundException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return "Locale not found. Reason: " + reason;
    }
}
