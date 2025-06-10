package com.timiremind.plerooo.task.service;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface TaskService {
    TaskResponseDto create(CreateTaskDto dto, UserDetails userDetail);
}
