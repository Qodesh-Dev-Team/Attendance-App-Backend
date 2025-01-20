package com.qcc.hallow.controller;

import com.qcc.hallow.dto.otpRequest.PasswordOtpVerifyDto;
import com.qcc.hallow.dto.user.UserCreateDto;
import com.qcc.hallow.dto.user.UserDto;
import com.qcc.hallow.dto.user.UserLoginDto;
import com.qcc.hallow.dto.user.UserPasswordUpdateDto;
import com.qcc.hallow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Sign Up", description = "Create a new user account")
    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<String> createNewUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return ResponseEntity.ok("User created successfully");
    }

    @Operation(summary = "Login", description = "Login to user account")
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        UserDto user = userService.login(userLoginDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update User", description = "Update user account")
    @RequestMapping(method = RequestMethod.POST, path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Reset Password", description = "Reset user password")
    @RequestMapping(method = RequestMethod.POST, path = "/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto) {
        userService.resetPassword(userPasswordUpdateDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Find User", description = "Find user by email")
    @RequestMapping(method = RequestMethod.GET, path = "/find/{email}")
    public ResponseEntity<UserDto> findUser(@PathVariable(value = "email") String email) {
        UserDto user = userService.findUser(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Find All Users", description = "Find all users")
    @RequestMapping(method = RequestMethod.GET, path = "/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @Operation(summary = "Delete User", description = "Delete user by email")
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Forgot Password", description = "Send password reset email")
    @RequestMapping(method = RequestMethod.POST, path = "/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable(value = "email") String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Email sent successfully");
    }

    @Operation(summary = "Verify Password OTP", description = "Verify password reset OTP")
    @RequestMapping(method = RequestMethod.POST, path = "/verifyPasswordOtp")
    public ResponseEntity<Boolean> verifyPasswordOtp(@RequestBody @Valid PasswordOtpVerifyDto passwordOtpVerifyDto) {
        boolean isOtpValid = userService.verifyPasswordOtp(passwordOtpVerifyDto);
        return ResponseEntity.ok(isOtpValid);
    }

    @Operation(summary = "Submit New Password", description = "Submit new password")
    @RequestMapping(method = RequestMethod.POST, path = "/submitNewPassword")
    public ResponseEntity<String> submitNewPassword(@RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto) {
        userService.submitNewPassword(userPasswordUpdateDto);
        return ResponseEntity.ok("Password updated successfully");
    }

}
