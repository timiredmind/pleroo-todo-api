package com.timiremind.plerooo.task.service;

import static com.timiremind.plerooo.core.util.DateUtil.getTimeAgoAtMidnight;
import static com.timiremind.plerooo.task.mapper.TaskMapper.mapEntityToResponseDto;

import com.timiremind.plerooo.core.exception.ResourceNotFoundException;
import com.timiremind.plerooo.core.util.PageableMeta;
import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.FetchTasksQueryDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.dto.UpdateTaskDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.repository.TaskRepository;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskServiceImpl implements TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public TaskResponseDto create(CreateTaskDto dto, UserDetails userDetail) {
        final String username = userDetail.getUsername();
        DatabaseUser user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        DatabaseTask task = taskRepository.save(dto, user);
        logger.info("User with id {} created task with id {}", user.getId(), task.getId());
        return mapEntityToResponseDto(task);
    }

    @Override
    public PageableMeta<TaskResponseDto> fetch(
            UserDetails userDetail, FetchTasksQueryDto queryDto) {
        final String username = userDetail.getUsername();
        final DatabaseUser user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        final int page = Math.max((queryDto.page() - 1), 0);
        final Pageable pageable =
                PageRequest.of(page, queryDto.limit(), Sort.by("timeCreated").descending());
        final long startTime =
                (queryDto.startTime() != null) ? queryDto.startTime() : getTimeAgoAtMidnight(7);
        final long endTime =
                (queryDto.endTime() != null) ? queryDto.endTime() : System.currentTimeMillis();
        final Page<DatabaseTask> taskPage =
                taskRepository.fetch(user, startTime, endTime, pageable);
        logger.info(
                "Retrieved tasks for user. UserId={}, count={}, page={}/{} timeRange=[{} - {}],",
                user.getId(),
                taskPage.getContent().size(),
                taskPage.getNumber(),
                taskPage.getTotalPages(),
                startTime,
                endTime);

        return new PageableMeta<>(
                mapEntityToResponseDto(taskPage.getContent()),
                taskPage.getTotalElements(),
                queryDto.page(),
                queryDto.limit(),
                taskPage.getTotalPages());
    }

    @Override
    public TaskResponseDto update(String id, UpdateTaskDto dto) {
        final DatabaseTask task =
                taskRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    logger.error("Task with id {} not found", id);
                                    return new ResourceNotFoundException("Task not found");
                                });

        updateTaskFields(dto, task);

        final DatabaseTask updatedTask = taskRepository.update(task);
        logger.info("Task updated. taskId={}, newValues={}", updatedTask.getId(), updatedTask);
        return mapEntityToResponseDto(updatedTask);
    }

    @Override
    public String delete(String id) {
        taskRepository.delete(id);
        logger.info("successfully deleted task with id {}", id);
        return "Task deleted";
    }

    private void updateTaskFields(UpdateTaskDto dto, DatabaseTask task) {
        task.setTitle((dto.title() != null) ? dto.title() : task.getTitle());
        task.setDescription(
                (dto.description() != null) ? dto.description() : task.getDescription());
        task.setPriority((dto.priority() != null) ? dto.priority() : task.getPriority());
        task.setStatus((dto.status() != null) ? dto.status() : task.getStatus());
        task.setDueDate((dto.dueDate() != null) ? dto.dueDate() : task.getDueDate());
    }
}
