package com.qcc.hallow.service.impl;

import com.qcc.hallow.dto.otpRequest.PasswordOtpVerifyDto;
import com.qcc.hallow.dto.user.*;
import com.qcc.hallow.entity.OTPRequest;
import com.qcc.hallow.entity.User;
import com.qcc.hallow.entity.enums.UserAccountStatus;
import com.qcc.hallow.repository.OtpRequestRepository;
import com.qcc.hallow.repository.UserRepository;
import com.qcc.hallow.service.EmailService;
import com.qcc.hallow.service.UserService;
import com.qcc.hallow.util.EmailContentGenerator;
import com.qcc.hallow.util.IdGenerator;
import com.qcc.hallow.util.OtpGenerator;
import com.qcc.hallow.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OtpRequestRepository otpRequestRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OtpRequestRepository otpRequestRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.otpRequestRepository = otpRequestRepository;
        this.emailService = emailService;
    }

    @Override
    public void createUser(UserCreateDto user) {
        if (userRepository.findUserByEmail(user.email()).isPresent()) {
            throw new IllegalArgumentException("User with email already exists");
        }
        User newUser = new User();
        newUser.setId(IdGenerator.generateId());
        newUser.setFirstname(user.firstname());
        newUser.setLastname(user.lastname());
        newUser.setEmail(user.email());
        newUser.setUserAccountType(user.userAccountType());
        newUser.setUserAccountStatus(UserAccountStatus.ACTIVE);
        try {
            newUser.setPassword(PasswordUtil.createHash(user.password()));
        } catch (PasswordUtil.CannotPerformOperationException e) {
            throw new RuntimeException(e);
        }

        userRepository.save(newUser);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        userRepository.delete(user);
    }

    @Override
    public void updateUser(String email, UserDto userDto) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException("User with email not found");
        }
        if (userDto.firstname() != null) {
            user.setFirstname(userDto.firstname());
        }
        if (userDto.lastname() != null) {
            user.setLastname(userDto.lastname());
        }
        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        if (userDto.userAccountType() != null) {
            user.setUserAccountType(userDto.userAccountType());
        }
        if (userDto.userAccountStatus() != null) {
            user.setUserAccountStatus(userDto.userAccountStatus());
        }
        userRepository.save(user);
    }

    @Override
    public void resetPassword(UserPasswordUpdateDto userPasswordUpdateDto) {
        User user = userRepository.findUserByEmail(userPasswordUpdateDto.email())
                .orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        try {
            if (!PasswordUtil.verifyPassword(userPasswordUpdateDto.oldPassword(), user.getPassword())) {
                throw new RuntimeException("Passwords don't match");
            } else {
                user.setPassword(PasswordUtil.createHash(userPasswordUpdateDto.newPassword()));
            }
        } catch (PasswordUtil.InvalidHashException | PasswordUtil.CannotPerformOperationException e) {
            throw new RuntimeException(e.getMessage());
        }

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void forgotPassword(String email) {
        userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        String otpCode = OtpGenerator.generateOtpCode();

        OTPRequest request = new OTPRequest();
        request.setEmail(email);
        request.setOtpCode(otpCode);
        request.setCreatedBy(email);

        otpRequestRepository.save(request);
        String emailBody = EmailContentGenerator.generateForgotPasswordContent(email.substring(0, email.indexOf('@')), otpCode);

        emailService.sendEmail(email, "Reset Password", emailBody);
    }

    @Override
    public boolean verifyPasswordOtp(PasswordOtpVerifyDto passwordOtpVerifyDto) {
        OTPRequest otpRequest = otpRequestRepository.findOTPRequestByEmailAndIsExpired(passwordOtpVerifyDto.email(), false)
                .orElseThrow(() -> new EntityNotFoundException("Otp Record not found"));
        return passwordOtpVerifyDto.otpCode().equals(otpRequest.getOtpCode());
    }

    @Override
    public void submitNewPassword(UserPasswordUpdateDto userPasswordUpdateDto) {
        User user = userRepository.findUserByEmail(userPasswordUpdateDto.email())
                .orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        try {
            user.setPassword(PasswordUtil.createHash(userPasswordUpdateDto.newPassword()));
        } catch (PasswordUtil.CannotPerformOperationException e) {
            throw new RuntimeException(e);
        }
        userRepository.save(user);
    }


    @Override
    public UserDto findUser(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        return new UserDto(user.getFirstname(), user.getLastname(), user.getEmail(), user.getUserAccountType(), user.getUserAccountStatus());
    }

    @Override
    public List<UserDto> findAllUsers() {
        return UserDtoMapper.maptoDtoList(userRepository.findAll());
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findUserByEmail(userLoginDto.email()).orElseThrow(() -> new EntityNotFoundException("User with email not found"));
        try {
            if (!PasswordUtil.verifyPassword(userLoginDto.password(), user.getPassword())) {
                throw new IllegalArgumentException("Passwords don't match");
            }
        } catch (PasswordUtil.InvalidHashException | PasswordUtil.CannotPerformOperationException e) {
            throw new RuntimeException(e.getMessage());
        }

        return UserDtoMapper.mapToDto(user);
    }
}
