package com.city.online.api.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class DateTimeUtil {

    public static final String GMT = "GMT";

    public static Timestamp getIstTime() {
        Calendar now = Calendar.getInstance();
        ZoneId toTimeZone = ZoneId.of(GMT);
        LocalDateTime today = LocalDateTime.now();
        ZonedDateTime currentDateTime = today.atZone(now.getTimeZone().toZoneId());
        ZonedDateTime currentIstTime = currentDateTime.withZoneSameInstant(toTimeZone);
        LocalDateTime withoutTimeZone = currentIstTime.toLocalDateTime();

        return Timestamp.valueOf(withoutTimeZone);
    }

    private DateTimeUtil() {
        super();
    }
}
