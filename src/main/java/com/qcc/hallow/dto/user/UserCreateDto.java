package com.qcc.hallow.dto.user;

import com.qcc.hallow.entity.enums.UserAccountStatus;
import com.qcc.hallow.entity.enums.UserAccountType;

public record UserCreateDto(String firstname,
                      String lastname,
                      String email,
                      String password,
                      UserAccountType userAccountType,
                      UserAccountStatus userAccountStatus) {
}

