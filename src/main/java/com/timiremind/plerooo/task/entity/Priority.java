package com.timiremind.plerooo.task.entity;

import java.util.Random;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    private static final Random PRNG = new Random();

    public static Priority randomPriority() {
        Priority[] priorities = values();
        return priorities[PRNG.nextInt(priorities.length)];
    }
}
