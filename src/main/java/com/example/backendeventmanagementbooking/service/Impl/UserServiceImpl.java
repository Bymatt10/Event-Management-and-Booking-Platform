package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.repository.UserRepository;
import com.example.backendeventmanagementbooking.security.JwtUtil;
import com.example.backendeventmanagementbooking.service.UserServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserLoginResponseDto> login(UserLoginDto userLoginDto) {
        var username = userLoginDto.getUsername();

        var userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            var password = userLoginDto.getPassword();
            var encodedPassword = userEntity.get().getPassword();

            log.info("User found, verifying password...");
            if (passwordEncoder.matches(password, encodedPassword)) {
                log.info("Password matches, generating token...");
                return Optional.of(new UserLoginResponseDto());
            }
        } else {
            log.warn("User not found: {}", username);
        }
        return Optional.empty();
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        var userEntity = objectMapper.convertValue(userRequestDto, UserEntity.class);
        var savedUser = userRepository.save(userEntity);
        return objectMapper.convertValue(savedUser, UserResponseDto.class);
    }


}
