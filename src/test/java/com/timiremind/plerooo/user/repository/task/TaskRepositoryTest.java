package com.timiremind.plerooo.user.repository.task;

import static com.timiredmind.pleroo.task.TaskTestData.testTaskDto;
import static com.timiredmind.pleroo.user.UserTestData.testDbUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.entity.Priority;
import com.timiremind.plerooo.task.entity.Status;
import com.timiremind.plerooo.task.repository.PostgresTaskRepository;
import com.timiremind.plerooo.task.repository.TaskRepository;
import com.timiremind.plerooo.user.RepositoryTest;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.PostgreUserRepository;
import java.time.Clock;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RepositoryTest
public class TaskRepositoryTest {
    @Autowired private Clock clock;
    @Autowired private TaskRepository taskRepository;
    @Autowired private PostgresTaskRepository postgresTaskRepository;
    @Autowired private PostgreUserRepository postgreUserRepository;

    @AfterEach
    void tearDown() {
        postgresTaskRepository.deleteAll();
        postgreUserRepository.deleteAll();
    }

    @Test
    void should_save_database_task() {
        final CreateTaskDto dto = testTaskDto();
        final DatabaseUser savedDbUser = postgreUserRepository.save(testDbUser());
        final DatabaseTask task = taskRepository.save(dto, savedDbUser);

        assertThat(task.getId()).isNotNull();
        assertThat(task.getTitle()).isEqualTo(dto.title());
        assertThat(task.getDescription()).isEqualTo(dto.description());
        assertThat(task.getUser()).isEqualTo(savedDbUser);
        assertThat(task.getPriority()).isEqualTo(dto.priority());
        assertThat(task.getStatus()).isEqualTo(dto.status());
        assertThat(task.getDueDate()).isEqualTo(dto.dueDate());
        assertThat(task.getTimeCreated()).isEqualTo(clock.millis());
        assertThat(task.getTimeUpdated()).isEqualTo(clock.millis());
    }

    @Test
    void should_update_database_task() {
        final CreateTaskDto dto = testTaskDto(Priority.LOW, Status.BACKLOG);
        final DatabaseUser savedDbUser = postgreUserRepository.save(testDbUser());
        final DatabaseTask dbTask = taskRepository.save(dto, savedDbUser);

        dbTask.setPriority(Priority.HIGH);
        dbTask.setStatus(Status.COMPLETED);
        dbTask.setTitle("Random Test Title");

        final DatabaseTask updatedTask = taskRepository.update(dbTask);
        assertThat(updatedTask.getId()).isEqualTo(dbTask.getId());
        assertThat(updatedTask.getTitle()).isEqualTo("Random Test Title");
        assertThat(updatedTask.getDescription()).isEqualTo(dbTask.getDescription());
        assertThat(updatedTask.getDueDate()).isEqualTo(dbTask.getDueDate());
        assertThat(updatedTask.getPriority()).isEqualTo(Priority.HIGH);
        assertThat(updatedTask.getStatus()).isEqualTo(Status.COMPLETED);
        assertThat(updatedTask.getTimeCreated()).isEqualTo(dbTask.getTimeCreated());
    }

    @Test
    void should_return_page() {
        final DatabaseUser savedDbUser = postgreUserRepository.save(testDbUser());

        for (long i = 1; i <= 20; i++) {
            final CreateTaskDto dto = testTaskDto();
            taskRepository.save(dto, savedDbUser);
        }

        Pageable pageable = PageRequest.of(1, 10, Sort.by("timeCreated").descending());

        Page<DatabaseTask> page =
                taskRepository.fetch(savedDbUser, clock.millis(), clock.millis(), pageable);

        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(20);
        assertThat(page.getContent().size()).isEqualTo(10);
    }

    @Test
    void should_return_empty_page() {
        final DatabaseUser savedDbUser = postgreUserRepository.save(testDbUser());
        Pageable pageable = PageRequest.of(1, 10, Sort.by("timeCreated").descending());

        Page<DatabaseTask> page =
                taskRepository.fetch(savedDbUser, clock.millis(), clock.millis(), pageable);

        assertThat(page.getTotalPages()).isEqualTo(0);
        assertThat(page.getTotalElements()).isEqualTo(0);
        assertThat(page.getContent().size()).isEqualTo(0);
    }

    @Test
    void should_find_task_by_id() {
        final DatabaseUser savedDbUser = postgreUserRepository.save(testDbUser());
        final CreateTaskDto dto = testTaskDto();
        final DatabaseTask task = taskRepository.save(dto, savedDbUser);

        final Optional<DatabaseTask> optionalDatabaseTask = taskRepository.findById(task.getId());
        assertThat(optionalDatabaseTask.isPresent()).isTrue();
    }

    @Test
    void should_return_empty_task_finding_by_invalid_id() {
        final Optional<DatabaseTask> optionalDatabaseTask =
                taskRepository.findById("random-test-id");
        assertThat(optionalDatabaseTask.isEmpty()).isTrue();
    }
}
