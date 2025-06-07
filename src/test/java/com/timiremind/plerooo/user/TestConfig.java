package com.timiremind.plerooo.user;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    private static final String FIXED_TIME = "2025-06-03T19:54:00Z";

    @Bean
    public Clock testClock() {
        return Clock.fixed(Instant.parse(FIXED_TIME), ZoneId.systemDefault());
    }
}
