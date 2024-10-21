package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

}
