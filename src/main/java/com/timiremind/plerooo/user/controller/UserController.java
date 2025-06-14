package com.timiremind.plerooo.user.controller;

import com.timiremind.plerooo.core.response.Response;
import com.timiremind.plerooo.user.dto.CreateUserRequestDto;
import com.timiremind.plerooo.user.dto.LoginDto;
import com.timiremind.plerooo.user.dto.LoginResponseDto;
import com.timiremind.plerooo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/user",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @RequestMapping(
            value = "/register",
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<String>> register(@RequestBody @Valid CreateUserRequestDto dto) {
        String response = userService.register(dto);
        return ResponseEntity.ok(new Response<>(true, response));
    }

    @RequestMapping(
            value = "/login",
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<LoginResponseDto>> login(@RequestBody @Valid LoginDto dto) {
        LoginResponseDto response = userService.login(dto);
        return ResponseEntity.ok(new Response<>(true, response));
    }
}
