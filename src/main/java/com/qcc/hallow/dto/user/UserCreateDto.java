package com.qcc.hallow.dto.user;

import com.qcc.hallow.entity.enums.UserAccountStatus;
import com.qcc.hallow.entity.enums.UserAccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@NotBlank String firstname,
                      @NotBlank String lastname,
                      @NotBlank String email,
                      @NotBlank String password,
                      @NotNull UserAccountType userAccountType,
                      @NotNull UserAccountStatus userAccountStatus) {
}

