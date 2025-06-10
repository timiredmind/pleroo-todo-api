package com.timiredmind.pleroo;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockTestData {
    private static final String FIXED_TIME = "2025-06-03T19:54:00Z";

    public static Clock testClock() {
        return Clock.fixed(Instant.parse(FIXED_TIME), ZoneId.systemDefault());
    }
}
