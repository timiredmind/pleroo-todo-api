package com.timiremind.plerooo.task.dto;

import com.timiremind.plerooo.task.entity.Priority;
import com.timiremind.plerooo.task.entity.Status;

public record TaskResponseDto(
        String id,
        String title,
        String description,
        Priority priority,
        Long dueDate,
        Status status,
        Long timeCreated) {}
