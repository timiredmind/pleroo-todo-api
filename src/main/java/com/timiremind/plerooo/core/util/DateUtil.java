package com.timiremind.plerooo.core.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getErrorTimeStamp(long millis) {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("GMT"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS 'GMT'"));
    }
}
