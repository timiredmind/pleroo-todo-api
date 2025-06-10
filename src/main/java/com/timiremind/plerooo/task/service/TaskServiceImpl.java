package com.timiremind.plerooo.task.service;

import static com.timiremind.plerooo.task.mapper.TaskMapper.mapEntityToResponseDto;

import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.task.repository.TaskRepository;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
