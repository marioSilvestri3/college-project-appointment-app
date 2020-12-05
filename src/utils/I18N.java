package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    public static final Locale locale;
    public static final ResourceBundle resourceBundle;
    public static final ZoneId zoneId;
    public static final ZoneOffset zoneOffset;
    public static final DateTimeFormatter formatter;
    public static final FormatStyle dateStyle = FormatStyle.MEDIUM;
    public static final FormatStyle timeStyle = FormatStyle.SHORT;

    static {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("utils.Language", locale);
        zoneId = ZoneId.systemDefault();
        zoneOffset = zoneId.getRules().getOffset(LocalDateTime.now());
        formatter = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle);
    }

    public static String getString(String string) {
        return resourceBundle.getString(string);
    }

    public LocalDateTime getFormattedTime(LocalDateTime ldt) {
        ldt.format(formatter);
        return ldt;
    }
}
