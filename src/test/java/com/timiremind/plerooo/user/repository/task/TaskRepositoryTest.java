package com.timiremind.plerooo.user.repository.task;

import static com.timiredmind.pleroo.task.TaskTestData.testTaskDto;
import static com.timiredmind.pleroo.user.UserTestData.testDbUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.repository.TaskRepository;
import com.timiremind.plerooo.user.RepositoryTest;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.PostgreUserRepository;
import java.time.Clock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class TaskRepositoryTest {
    @Autowired private Clock clock;
    @Autowired private TaskRepository taskRepository;
    @Autowired private PostgreUserRepository postgreUserRepository;

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
}
