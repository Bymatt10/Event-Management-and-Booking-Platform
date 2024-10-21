package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.PATTERN_SECURE_PASSWORD;
import static com.example.backendeventmanagementbooking.config.ConstantsVariables.PATTERN_SECURE_PASSWORD_MESSAGE;

@Data
public class UserLoginDto {

    @NotBlankWithFieldName
    private String username;

    @NotBlankWithFieldName
    @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
    private String password;
}
