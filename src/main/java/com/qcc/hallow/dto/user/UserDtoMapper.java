package com.qcc.hallow.dto.user;

import com.qcc.hallow.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getUserAccountType(),
                user.getUserAccountStatus()
        );
    }

    public static List<UserDto> maptoDtoList(List<User> users) {
        if(users == null) {
            return null;
        }
        return users.stream()
                .map(UserDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setEmail(userDto.email());
        user.setUserAccountType(userDto.userAccountType());
        user.setUserAccountStatus(userDto.userAccountStatus());
        return user;
    }
}
