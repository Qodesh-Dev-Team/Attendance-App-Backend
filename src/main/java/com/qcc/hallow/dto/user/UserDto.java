package com.qcc.hallow.dto.user;

import com.qcc.hallow.entity.enums.UserAccountStatus;
import com.qcc.hallow.entity.enums.UserAccountType;

public record UserDto(String firstname,
                      String lastname,
                      String email,
                      UserAccountType userAccountType,
                      UserAccountStatus userAccountStatus) {
}
