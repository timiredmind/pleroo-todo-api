package com.timiremind.plerooo.user.service;

import com.timiremind.plerooo.core.exception.InvalidAuthenticationException;
import com.timiremind.plerooo.core.exception.RegistrationException;
import com.timiremind.plerooo.user.dto.CreateUserRequestDto;
import com.timiremind.plerooo.user.dto.LoginDto;
import com.timiremind.plerooo.user.dto.LoginResponseDto;

public interface UserService {

    String register(CreateUserRequestDto dto) throws RegistrationException;

    LoginResponseDto login(LoginDto dto) throws InvalidAuthenticationException;
}
