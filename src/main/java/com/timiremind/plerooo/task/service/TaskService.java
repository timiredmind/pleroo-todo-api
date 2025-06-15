package com.timiremind.plerooo.task.service;

import com.timiremind.plerooo.core.util.PageableMeta;
import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.FetchTasksQueryDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.dto.UpdateTaskDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface TaskService {
    TaskResponseDto create(CreateTaskDto dto, UserDetails userDetail);

    PageableMeta<TaskResponseDto> fetch(UserDetails userDetails, FetchTasksQueryDto queryDto);

    TaskResponseDto update(String id, UpdateTaskDto updateTaskDto);

    String delete(String id);
}
