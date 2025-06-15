package com.timiremind.plerooo.task.repository;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {
    DatabaseTask save(CreateTaskDto dto, DatabaseUser user);

    DatabaseTask update(DatabaseTask databaseTask);

    Page<DatabaseTask> fetch(DatabaseUser user, long startTime, long endTime, Pageable pageable);

    Optional<DatabaseTask> findById(String id);

    void delete(String id);
}
