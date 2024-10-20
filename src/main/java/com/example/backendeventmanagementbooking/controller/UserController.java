package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.service.UserServiceInterface;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.backendeventmanagementbooking.utils.GenericResponse.GenerateResponse;

@RestController
@RequestMapping("api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @PostMapping("register")
    public ResponseEntity<GenericResponse<UserResponseDto>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.registerUser(userRequestDto);
        return GenerateResponse(HttpStatus.OK, userResponseDto);
    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse<String>> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        var loginResponse = userService.login(userLoginDto);
        if (loginResponse.isPresent()) {
            String token = loginResponse.get().getToken();
            return GenerateResponse(HttpStatus.OK, token);
        } else {
            return GenerateResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

}
