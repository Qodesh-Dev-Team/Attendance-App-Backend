package com.qcc.hallow.dto.user;

public record UserPasswordUpdateDto(String email,
                                    String oldPassword,
                                    String newPassword) {
}
