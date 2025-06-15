package com.timiremind.plerooo.task.controller;

import com.timiremind.plerooo.core.response.Response;
import com.timiremind.plerooo.core.util.PageableMeta;
import com.timiremind.plerooo.task.dto.CreateTaskDto;
import com.timiremind.plerooo.task.dto.FetchTasksQueryDto;
import com.timiremind.plerooo.task.dto.TaskResponseDto;
import com.timiremind.plerooo.task.dto.UpdateTaskDto;
import com.timiremind.plerooo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(
            value = "",
            method = {RequestMethod.GET})
    public ResponseEntity<Response<PageableMeta<TaskResponseDto>>> fetch(
            @ModelAttribute FetchTasksQueryDto dto, Authentication authentication) {
        final PageableMeta<TaskResponseDto> response =
                service.fetch((UserDetails) authentication.getPrincipal(), dto);
        return ResponseEntity.ok(new Response<>(true, response));
    }

    @RequestMapping(
            value = "/{id}",
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<TaskResponseDto>> update(
            @PathVariable String id, @RequestBody UpdateTaskDto dto) {
        final TaskResponseDto responseDto = service.update(id, dto);
        return ResponseEntity.ok(new Response<>(true, responseDto));
    }

    @RequestMapping(
            value = "/{id}",
            method = {RequestMethod.DELETE})
    public ResponseEntity<Response<String>> delete(@PathVariable String id) {
        final String response = service.delete(id);
        return ResponseEntity.ok(new Response<>(true, response));
    }
}
