package ru.otlnal.onlineloans.system.config;

import java.util.Locale;

/**
 * Application constants.
 */
public final class Constants {

    public static final String PHONE_NUMBER_REGEX = "^9\\d{9}$";
    public static final String SNILS_REGEX = "\\d{3}-\\d{3}-\\d{3} \\d{2}";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anon";

    public static final Locale LOCALE = new Locale("ru");

    private Constants() {
    }
}
