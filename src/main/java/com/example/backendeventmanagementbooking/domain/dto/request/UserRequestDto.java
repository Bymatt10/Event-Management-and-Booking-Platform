package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.*;

@Data
public class UserRequestDto {
    @NotBlankWithFieldName
    private String username;

    @NotBlankWithFieldName
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotBlankWithFieldName
    @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
    private String password;

    @NotBlankWithFieldName
    private String identification;

    @NotBlankWithFieldName
    private String name;

    @NotBlankWithFieldName
    private String lastName;

    @NotBlankWithFieldName
    @Pattern(regexp = PATTERN_PHONE_NUMBER_NIC, message = PATTERN_PHONE_NUMBER_NIC_MESSAGE)
    private String phoneNumber;

    private String address;
}
