package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.request.UserChangePasswordDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserChangePasswordResponse;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

import java.util.UUID;

public interface SecurityService {
    GenericResponse<UserLoginResponseDto> login(UserLoginDto userLoginDto);

    GenericResponse<UserResponseDto> registerUser(UserRequestDto userRequestDto);

    GenericResponse<UserChangePasswordResponse> changePassword(UserChangePasswordDto userChangePasswordDto, UUID userId);
}
