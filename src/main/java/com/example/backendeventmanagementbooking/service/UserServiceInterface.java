package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;

import java.util.Optional;

public interface UserServiceInterface {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserLoginResponseDto> login(UserLoginDto userLoginDto);

    UserResponseDto registerUser(UserRequestDto userRequestDto);

}
