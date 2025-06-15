package com.timiremind.plerooo.task.dto;

import com.timiremind.plerooo.task.entity.Priority;
import com.timiremind.plerooo.task.entity.Status;

public record UpdateTaskDto(
        String title, String description, Priority priority, Status status, Long dueDate) {}
