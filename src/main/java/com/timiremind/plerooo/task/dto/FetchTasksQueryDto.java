package com.timiremind.plerooo.task.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FetchTasksQueryDto(
        @Min(1) int page, @Min(10) @Max(50) int limit, Long startTime, Long endTime) {}
