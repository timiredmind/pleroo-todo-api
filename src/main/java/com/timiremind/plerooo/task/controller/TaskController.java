package com.timiremind.plerooo.task.controller;

import com.timiremind.plerooo.core.response.Response;
import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/task",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
    private final TaskService service;

    @RequestMapping(
            value = "",
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<TaskResponseDto>> create(
            @RequestBody CreateTaskDto dto, Authentication authentication) {
        final TaskResponseDto response =
                service.create(dto, (UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new Response<>(true, response));
    }
}
