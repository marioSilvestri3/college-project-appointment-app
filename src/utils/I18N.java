package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for standardizing i18n timezones, datetime formatting, and language translation.
 * Author: Mario Silvestri III
 */
public class I18N {
    public static final Locale locale;
    public static final ResourceBundle resourceBundle;
    public static final ZoneId SYSTEM_ZONE;
    public static final ZoneId NY_ZONE = ZoneId.of("America/New_York");
    public static final ZoneOffset zoneOffset;
    public static final DateTimeFormatter formatter;
    public static final FormatStyle dateStyle = FormatStyle.MEDIUM;
    public static final FormatStyle timeStyle = FormatStyle.SHORT;
    public static final LocalTime NYOpen = LocalTime.of(8, 0);
    public static final LocalTime NYClose = LocalTime.of(22, 0);
    public static final ObservableList<String> hh = FXCollections.observableArrayList();
    public static final ObservableList<String> mm = FXCollections.observableArrayList();
    public static final ObservableList<String> ampm = FXCollections.observableArrayList();

    static {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("utils.Language", locale);
        SYSTEM_ZONE = ZoneId.systemDefault();
        zoneOffset = SYSTEM_ZONE.getRules().getOffset(LocalDateTime.now());
        formatter = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle);

        for (LocalTime lt = LocalTime.of(0,0); lt.isBefore(LocalTime.of(12,0)); lt = lt.plusMinutes(60)) {
            hh.add(lt.format(DateTimeFormatter.ofPattern("hh")));
        }

        for (LocalTime lt = LocalTime.of(0,0); lt.isBefore(LocalTime.of(1,0)); lt = lt.plusMinutes(5)) {
            mm.add(lt.format(DateTimeFormatter.ofPattern("mm")));
        }

        ampm.add(LocalTime.of(0,0).format(DateTimeFormatter.ofPattern("a")));
        ampm.add(LocalTime.of(12,0).format(DateTimeFormatter.ofPattern("a")));
    }

    /**
     * Gets localized language translation of a word or phrase.
     * @param string The english string to be translated
     * @return Translated string
     */
    public static String getString(String string) {
        return resourceBundle.getString(string);
    }

    /**
     * Returns a formatted version of a LocalDateTime
     * @param ldt The LocalDateTime to format
     * @return String value of formatted datetime
     */
    public static LocalDateTime getFormattedTime(LocalDateTime ldt) {
        ldt.format(formatter);
        return ldt;
    }
}
