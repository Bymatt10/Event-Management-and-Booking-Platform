package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.exception.AuthException;
import jakarta.validation.constraints.Pattern;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.PATTERN_SECURE_PASSWORD;
import static com.example.backendeventmanagementbooking.config.ConstantsVariables.PATTERN_SECURE_PASSWORD_MESSAGE;

public record UserChangePasswordDto(
        @NotBlankWithFieldName
        @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
        String oldPassword,

        @NotBlankWithFieldName
        @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
        String newPassword,

        @NotBlankWithFieldName
        @Pattern(regexp = PATTERN_SECURE_PASSWORD, message = PATTERN_SECURE_PASSWORD_MESSAGE)
        String confirmPassword) {

    public void equalsPassword() {
        if (oldPassword.equals(confirmPassword)) {
            throw new AuthException("New password cannot be the same as the old password");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new AuthException("Passwords do not match");
        }
    }
}
