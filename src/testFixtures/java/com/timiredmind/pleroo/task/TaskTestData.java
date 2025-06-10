package com.timiredmind.pleroo.task;

import static com.timiredmind.pleroo.ClockTestData.testClock;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.entity.Priority;
import com.timiremind.plerooo.task.entity.Status;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import net.datafaker.Faker;

public class TaskTestData {
    private static final Faker faker = new Faker();

    public static CreateTaskDto testTaskDto() {
        String title = faker.expression("#{letterify '?????'}");
        String description = faker.expression("#{letterify '????????????????? ???? ???'}");
        return CreateTaskDto.builder()
                .title(title)
                .description(description)
                .dueDate(faker.random().nextLong())
                .priority(Priority.randomPriority())
                .status(Status.randomStatus())
                .build();
    }

    public static DatabaseTask testDbTask(CreateTaskDto dto, DatabaseUser user) {
        return DatabaseTask.builder()
                .id(faker.internet().uuid())
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .priority(dto.priority())
                .user(user)
                .dueDate(dto.dueDate())
                .timeCreated(testClock().millis())
                .timeUpdated(testClock().millis())
                .build();
    }
}
