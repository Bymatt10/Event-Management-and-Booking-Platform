package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.request.UserChangePasswordDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserChangePasswordResponse;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.service.SecurityService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.backendeventmanagementbooking.utils.GenericResponse.GenerateResponse;

@RestController
@RequestMapping("api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final SecurityService securityService;

    @PostMapping("register")
    public ResponseEntity<GenericResponse<UserResponseDto>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return securityService.registerUser(userRequestDto).GenerateResponse();

    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        return securityService.login(userLoginDto).GenerateResponse();
    }

    @PutMapping("change/password")
    public GenericResponse<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto, UUID userId) {
        return securityService.changePassword(userChangePasswordDto,userId);
    }

}
