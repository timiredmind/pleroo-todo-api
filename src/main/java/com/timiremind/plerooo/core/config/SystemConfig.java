package com.timiremind.plerooo.core.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SystemConfig {

    @Profile({"!test"})
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
