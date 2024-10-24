package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.common.BuildEmailDto;
import com.example.backendeventmanagementbooking.domain.dto.request.EmailDetailsDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserChangePasswordDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserLoginDto;
import com.example.backendeventmanagementbooking.domain.dto.request.UserRequestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserLoginResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserMeResponseDto;
import com.example.backendeventmanagementbooking.domain.dto.response.UserResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.enums.EmailFileNameTemplate;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.UserRepository;
import com.example.backendeventmanagementbooking.security.CustomUserDetailService;
import com.example.backendeventmanagementbooking.security.JwtUtil;
import com.example.backendeventmanagementbooking.service.SecurityService;
import com.example.backendeventmanagementbooking.utils.BuildEmail;
import com.example.backendeventmanagementbooking.utils.EmailSender;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

import static com.example.backendeventmanagementbooking.enums.EmailFileNameTemplate.REGISTER_USER;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;
    private final EmailSender emailSender;
    private final BuildEmail buildEmail;

    @Override
    public GenericResponse<Object> changePassword(UserChangePasswordDto userChangePasswordDto) {
        userChangePasswordDto.equalsPassword();

        var username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (!passwordEncoder.matches(userChangePasswordDto.oldPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "The old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(userChangePasswordDto.newPassword()));
        userRepository.save(user);
        return new GenericResponse<>(HttpStatus.OK);
    }

    public GenericResponse<UserLoginResponseDto> login(UserLoginDto userLoginDto) {
        var username = userLoginDto.getUsername();
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException(username);
        }
        var userDetails = customUserDetailService.generateUserDetails(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRole());

        var token = jwtUtil.generateToken(userDetails);
        return new GenericResponse<>(HttpStatus.OK, new UserLoginResponseDto(token));
    }

    @Override
    public GenericResponse<UserResponseDto> registerUser(UserRequestDto userRequestDto) throws IOException, MessagingException {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        var userEntity = objectMapper.convertValue(userRequestDto, UserEntity.class);
        var savedUser = userRepository.save(userEntity);
        var bodyEmail = buildEmail.getTemplateEmail(REGISTER_USER,
                new BuildEmailDto("#USERNAME", savedUser.getUsername()),
                new BuildEmailDto("#Date", new Date().toString())
        );
        emailSender.sendMail(new EmailDetailsDto(savedUser.getEmail(), bodyEmail, "Bienvenido"));

        var response = objectMapper.convertValue(savedUser, UserResponseDto.class);
        return new GenericResponse<>(HttpStatus.OK, response);
    }

    @Override
    public GenericResponse<UserMeResponseDto> me() {
        var username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new GenericResponse<>(HttpStatus.OK,
                new UserMeResponseDto(user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole())
        );
    }
}
