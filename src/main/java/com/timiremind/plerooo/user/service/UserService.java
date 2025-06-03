package com.timiremind.plerooo.user.service;

import com.timiremind.plerooo.core.exception.RegistrationException;
import com.timiremind.plerooo.user.dto.CreateUserRequestDto;

public interface UserService {

    String register(CreateUserRequestDto dto) throws RegistrationException;
}
