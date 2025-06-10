package com.timiredmind.pleroo.user;

import static com.timiredmind.pleroo.ClockTestData.testClock;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import net.datafaker.Faker;

public class UserTestData {
    private static final Faker faker = new Faker();

    public static DatabaseUser testDbUser() {
        return DatabaseUser.builder()
                .username(faker.internet().username())
                .password(faker.internet().password())
                .timeCreated(testClock().millis())
                .timeUpdated(testClock().millis())
                .build();
    }
}
