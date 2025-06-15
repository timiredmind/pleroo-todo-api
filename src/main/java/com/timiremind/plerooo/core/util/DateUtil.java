package com.timiremind.plerooo.core.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
    public static String getErrorTimeStamp(long millis) {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("GMT"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS 'GMT'"));
    }

    public static long getTimeAgoAtMidnight(int days) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -days);
        setCalendarToMidnight(cal);

        return cal.getTimeInMillis();
    }

    private static void setCalendarToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }
}
