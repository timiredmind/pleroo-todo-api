package com.timiremind.plerooo.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CreateUserRequestDto(
        @NotBlank String username, @NotBlank @Length(min = 8) String password) {}
