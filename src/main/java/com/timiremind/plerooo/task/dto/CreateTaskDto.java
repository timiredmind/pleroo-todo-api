package com.timiremind.plerooo.task.dto;

import com.timiremind.plerooo.task.entity.Priority;
import com.timiremind.plerooo.task.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateTaskDto(
        @NotBlank String title,
        String description,
        @NotNull Priority priority,
        @NotNull Status status,
        Long dueDate) {}
