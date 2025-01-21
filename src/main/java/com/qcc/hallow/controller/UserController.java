package com.qcc.hallow.controller;

import com.qcc.hallow.dto.otpRequest.PasswordOtpVerifyDto;
import com.qcc.hallow.dto.user.UserCreateDto;
import com.qcc.hallow.dto.user.UserDto;
import com.qcc.hallow.dto.user.UserLoginDto;
import com.qcc.hallow.dto.user.UserPasswordUpdateDto;
import com.qcc.hallow.service.UserService;
import com.qcc.hallow.util.ObjectUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "User created successfully"))),
            @ApiResponse(responseCode = "400", description = "Validation failed. Please check the input.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "01",
                        "status": "Error",
                        "message": "Validation failed. Please check the input.",
                        "errors": {
                            "password": "must not be blank",
                            "firstname": "must not be blank",
                            "userAccountStatus": "must not be null",
                            "userAccountType": "must not be null",
                            "email": "must not be blank",
                            "lastname": "must not be blank"
                        }
                    }
                    """))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "Forbidden: Invalid API Key"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "03",
                        "status": "Error",
                        "message": "No static resource."
                    }
                    """))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                                        {
                        "success": false,
                        "code": "09",
                        "status": "Error",
                        "message": "User with email already exists"
                    }
                                        """))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "Internal Server Error"))),

    })

    @Operation(summary = "Sign Up", description = "Create a new user account")
    @PostMapping("/signup")
    public ResponseEntity<String> createNewUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return ResponseEntity.ok("User created successfully");
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "firstname": "String",
                        "lastname": "String",
                        "email": "String",
                        "userAccountType": "ADMIN",
                        "userAccountStatus": "ACTIVE"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "01",
                        "status": "Error",
                        "message": "Validation failed. Please check the input.",
                        "errors": {
                            "password": "must not be blank",
                            "email": "must not be blank"
                        }
                    }
                    """))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "Forbidden: Invalid API Key"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                                {
                        "success": false,
                        "code": "05",
                        "status": "Error",
                        "message": "User does not exist."
                    }"""))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "03",
                        "status": "Error",
                        "message": "Passwords don't match"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "text/plain", examples = @ExampleObject(value = "Internal Server Error")))
    })
    @Operation(summary = "Login", description = "Login to user account")
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        UserDto user = userService.login(userLoginDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update User", description = "Update user account")
    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "email", required = true) String email, @RequestBody @Valid UserDto userDto) {
        if (ObjectUtil.isObjectNullOrEmpty(userDto)) {
            return ResponseEntity.noContent().build();
        }
        userService.updateUser(email, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Reset Password", description = "Reset user password")
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto) {
        userService.resetPassword(userPasswordUpdateDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "firstname": "  ",
                        "lastname": "String",
                        "email": "String",
                        "userAccountType": "USER",
                        "userAccountStatus": "ACTIVE"
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "User does not exist", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "05",
                        "status": "Error",
                        "message": "User does not exist."
                    }
                    """))),
            @ApiResponse(responseCode = "403", description = "Forbidden: Invalid API Key", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "text/plain"))
    })
    @Operation(summary = "Find User", description = "Find user by email")
    @GetMapping("/find/{email}")
    public ResponseEntity<UserDto> findUser(@PathVariable(value = "email") String email) {
        UserDto user = userService.findUser(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Find All Users", description = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    [
                        {
                            "firstname": "String",
                            "lastname": "String",
                            "email": "String",
                            "userAccountType": "ADMIN",
                            "userAccountStatus": "ACTIVE"
                        },
                        {
                            "firstname": "String",
                            "lastname": "String",
                            "email": "String",
                            "userAccountType": "USER",
                            "userAccountStatus": "ACTIVE"
                        }
                    ]
                    """))),
            @ApiResponse(responseCode = "204", description = "No User Records", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden: Invalid API Key", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> allUsers = userService.findAllUsers();

        if (allUsers.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(allUsers); // 200 OK
    }

    @Operation(summary = "Delete User", description = "Delete user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "User does not exist", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "code": "05",
                        "status": "Error",
                        "message": "User does not exist."
                    }
                    """))),
            @ApiResponse(responseCode = "403", description = "Forbidden: Invalid API Key", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Forgot Password", description = "Send password reset email")
    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable(value = "email") String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Email sent successfully");
    }

    @Operation(summary = "Verify Password OTP", description = "Verify password reset OTP")
    @PostMapping("/verifyPasswordOtp")
    public ResponseEntity<Boolean> verifyPasswordOtp(@RequestBody @Valid PasswordOtpVerifyDto passwordOtpVerifyDto) {
        boolean isOtpValid = userService.verifyPasswordOtp(passwordOtpVerifyDto);
        return ResponseEntity.ok(isOtpValid);
    }

    @Operation(summary = "Submit New Password", description = "Submit new password")
    @PostMapping("/submitNewPassword")
    public ResponseEntity<String> submitNewPassword(@RequestBody @Valid UserPasswordUpdateDto userPasswordUpdateDto) {
        userService.submitNewPassword(userPasswordUpdateDto);
        return ResponseEntity.ok("Password updated successfully");
    }

}
