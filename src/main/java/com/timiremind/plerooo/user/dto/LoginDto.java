package com.timiremind.plerooo.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(@NotBlank String username, @NotBlank String password) {}
