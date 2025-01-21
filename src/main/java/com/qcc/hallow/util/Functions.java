package com.qcc.hallow.util;

import com.qcc.hallow.dto.user.UserDto;

public class Functions {

    public static boolean isUserDtoNullOrEmpty(UserDto userDto) {
        return userDto.email() == null || userDto.email().isEmpty() || userDto.firstname() == null
                || userDto.firstname().isEmpty() || userDto.lastname() == null || userDto.lastname().isEmpty()
                || userDto.userAccountType() == null 
                || userDto.userAccountStatus() == null;
    }

}
