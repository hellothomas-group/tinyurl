package xyz.hellothomas.tinyurl.generator.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }


    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return new Date();
        } else {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return Date.from(instant);
        }
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return LocalDateTime.now();
        } else {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

}
