package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.request.UserChangePasswordDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserChangePasswordResponse;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.repository.UserRepository;
import com.example.backendeventmanagementbooking.security.CustomUserDetailService;
import com.example.backendeventmanagementbooking.security.JwtUtil;
import com.example.backendeventmanagementbooking.service.SecurityService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    public SecurityServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, ObjectMapper objectMapper, PasswordEncoder passwordEncoder, CustomUserDetailService customUserDetailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public GenericResponse<UserChangePasswordResponse> changePassword(UserChangePasswordDto userChangePasswordDto, UUID userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (userChangePasswordDto.oldPassword().equals(userChangePasswordDto.newPassword())) {
            throw new RuntimeException("The new password cannot be the same as the old password");
        }
        if (!passwordEncoder.matches(userChangePasswordDto.oldPassword(), user.get().getPassword())) {
            throw new RuntimeException("The old password is incorrect");
        }
        user.get().setPassword(passwordEncoder.encode(userChangePasswordDto.newPassword()));
        var savedPassword = userRepository.save(user.get());
        return new GenericResponse<>(HttpStatus.OK, new UserChangePasswordResponse(savedPassword));
    }


    public GenericResponse<UserLoginResponseDto> login(UserLoginDto userLoginDto) {
        var username = userLoginDto.getUsername();
        var userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), userEntity.get().getPassword())) {
            throw new BadCredentialsException(username);
        }
        var userDetails = customUserDetailService.generateUserDetails(userEntity.get().getUsername(), userEntity.get().getPassword(), userEntity.get().getRole());
        var token = jwtUtil.generateToken(userDetails);
        return new GenericResponse<>(HttpStatus.OK, new UserLoginResponseDto(token));
    }

    @Override
    public GenericResponse<UserResponseDto> registerUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        var userEntity = objectMapper.convertValue(userRequestDto, UserEntity.class);
        var savedUser = userRepository.save(userEntity);
        var response = objectMapper.convertValue(savedUser, UserResponseDto.class);
        return new GenericResponse<>(HttpStatus.OK, response);
    }


}
