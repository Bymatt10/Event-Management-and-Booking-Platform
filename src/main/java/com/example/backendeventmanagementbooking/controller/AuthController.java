package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.request.UserChangePasswordDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserMeResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.service.SecurityService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    @PostMapping("register")
    public ResponseEntity<GenericResponse<UserResponseDto>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) throws IOException, MessagingException {
        return securityService.registerUser(userRequestDto).GenerateResponse();

    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        return securityService.login(userLoginDto).GenerateResponse();
    }

    @PutMapping("change/password")
    public ResponseEntity<GenericResponse<Object>> changePassword(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto) {
        return securityService.changePassword(userChangePasswordDto).GenerateResponse();
    }

    @PostMapping("me")
    public ResponseEntity<GenericResponse<UserMeResponseDto>> me() {
        return securityService.me().GenerateResponse();
    }

}
