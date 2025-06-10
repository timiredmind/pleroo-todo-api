package com.timiremind.plerooo.task.entity;

import java.util.Random;

public enum Status {
    IN_PROGRESS,
    COMPLETED,
    BACKLOG;

    private static final Random PRNG = new Random();

    public static Status randomStatus() {
        Status[] statuses = values();
        return statuses[PRNG.nextInt(statuses.length)];
    }
}
