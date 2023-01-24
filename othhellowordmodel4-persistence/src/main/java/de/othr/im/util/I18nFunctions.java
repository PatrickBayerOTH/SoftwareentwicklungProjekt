package de.othr.im.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class I18nFunctions {

    public static String localizeTemperature(float temperature, Locale locale) {
        DecimalFormat df = new DecimalFormat("0.00");
        if(locale.equals(Locale.US)) {
            float fahrenheit = (((float) temperature * 9) / 5) + 32;
            String output = df.format(fahrenheit) + " °F";
            return output;
        } else {
            String output = df.format(temperature) + " °C";
            return output;
        }
    }
    public static String localizeTemperature(double temperature, Locale locale) {
        return localizeTemperature((float)temperature, locale);
    }
    public static String localizeTemperature(int temperature, Locale locale) {
        return localizeTemperature((float)temperature, locale);
    }
    public static String localizeTemperature(String temperature, Locale locale) {
        return localizeTemperature(Float.parseFloat(temperature), locale);
    }

    public static String localizeDate(Timestamp timestamp, Locale locale) {
        String localDate = DateTimeFormatter.ISO_LOCAL_DATE.format(timestamp.toLocalDateTime());
        return localDate;
    }
}
