package com.timiremind.plerooo.task.repository;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import java.time.Clock;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskRepositoryImpl implements TaskRepository {

    private final PostgresTaskRepository postgresTaskRepository;
    private final Clock clock;

    @Override
    public DatabaseTask save(CreateTaskDto dto, DatabaseUser user) {
        final DatabaseTask newTask =
                DatabaseTask.builder()
                        .title(dto.title())
                        .description(dto.description())
                        .user(user)
                        .priority(dto.priority())
                        .status(dto.status())
                        .dueDate(dto.dueDate())
                        .timeCreated(clock.millis())
                        .timeUpdated(clock.millis())
                        .build();

        return postgresTaskRepository.save(newTask);
    }

    @Override
    public DatabaseTask update(DatabaseTask task) {
        task.setTimeUpdated(clock.millis());
        return postgresTaskRepository.save(task);
    }

    @Override
    public Page<DatabaseTask> fetch(
            DatabaseUser user, long startTime, long endTime, Pageable pageable) {
        return postgresTaskRepository.findAllByUserAndTimeCreatedBetween(
                user, startTime, endTime, pageable);
    }

    @Override
    public Optional<DatabaseTask> findById(String id) {
        return postgresTaskRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        postgresTaskRepository.deleteById(id);
    }
}
