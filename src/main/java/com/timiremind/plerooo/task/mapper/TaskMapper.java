package com.timiremind.plerooo.task.mapper;

import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;

public class TaskMapper {
    public static TaskResponseDto mapEntityToResponseDto(DatabaseTask task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate(),
                task.getStatus(),
                task.getTimeCreated());
    }
}
