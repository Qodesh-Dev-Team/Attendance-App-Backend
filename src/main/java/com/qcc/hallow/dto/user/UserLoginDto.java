package com.qcc.hallow.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(@NotBlank String email, @NotBlank String password) {
}
