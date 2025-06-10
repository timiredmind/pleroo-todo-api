package com.timiremind.plerooo.user.service.task;

import static com.timiredmind.pleroo.task.TaskTestData.testDbTask;
import static com.timiredmind.pleroo.task.TaskTestData.testTaskDto;
import static com.timiredmind.pleroo.user.UserTestData.testDbUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.repository.TaskRepository;
import com.timiremind.plerooo.task.service.TaskService;
import com.timiremind.plerooo.task.service.TaskServiceImpl;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.entity.UserInfoDetails;
import com.timiremind.plerooo.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TaskServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final TaskRepository taskRepository = mock(TaskRepository.class);

    private final TaskService taskService = new TaskServiceImpl(userRepository, taskRepository);

    @Test
    void should_successfully_create_task() {
        final CreateTaskDto testDto = testTaskDto();
        final DatabaseUser databaseUser = testDbUser();
        final UserInfoDetails userInfoDetails = new UserInfoDetails(databaseUser);
        final DatabaseTask testDbTask = testDbTask(testDto, databaseUser);

        when(userRepository.findByUsername(databaseUser.getUsername()))
                .thenReturn(Optional.of(databaseUser));

        when(taskRepository.save(testDto, databaseUser)).thenReturn(testDbTask);
        final TaskResponseDto responseDto = taskService.create(testDto, userInfoDetails);

        assertThat(responseDto.id()).isNotNull();
        assertThat(responseDto.title()).isEqualTo(testDto.title());
        assertThat(responseDto.description()).isEqualTo(testDto.description());
        assertThat(responseDto.status()).isEqualTo(testDto.status());
        assertThat(responseDto.priority()).isEqualTo(testDto.priority());
        assertThat(responseDto.dueDate()).isEqualTo(testDto.dueDate());

        verify(userRepository, times(1)).findByUsername(databaseUser.getUsername());
        verify(taskRepository, times(1)).save(testDto, databaseUser);
    }

    @Test
    void should_throw_error_for_invalid_user() {
        final CreateTaskDto testDto = testTaskDto();
        final DatabaseUser databaseUser = testDbUser();
        final UserInfoDetails userInfoDetails = new UserInfoDetails(databaseUser);

        when(userRepository.findByUsername(databaseUser.getUsername()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.create(testDto, userInfoDetails))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository, times(1)).findByUsername(databaseUser.getUsername());
        verify(taskRepository, times(0)).save(testDto, databaseUser);
    }
}
