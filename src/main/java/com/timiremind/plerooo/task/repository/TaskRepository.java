package com.timiremind.plerooo.task.repository;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.user.entity.DatabaseUser;

public interface TaskRepository {
    DatabaseTask save(CreateTaskDto dto, DatabaseUser user);
}
