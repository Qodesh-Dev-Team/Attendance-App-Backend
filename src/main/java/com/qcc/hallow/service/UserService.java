package com.qcc.hallow.service;

import com.qcc.hallow.dto.otpRequest.PasswordOtpVerifyDto;
import com.qcc.hallow.dto.user.UserCreateDto;
import com.qcc.hallow.dto.user.UserDto;
import com.qcc.hallow.dto.user.UserLoginDto;
import com.qcc.hallow.dto.user.UserPasswordUpdateDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    void createUser(UserCreateDto user);

    void deleteUser(String email);

    void updateUser(UserDto user);

    void resetPassword(UserPasswordUpdateDto userPasswordUpdateDto);

    void forgotPassword(String email);

    boolean verifyPasswordOtp(PasswordOtpVerifyDto passwordOtpVerifyDto);

    void submitNewPassword(UserPasswordUpdateDto userPasswordUpdateDto);

    UserDto findUser(String email);

    List<UserDto> findAllUsers();

    UserDto login(@Valid UserLoginDto userLoginDto);
}
