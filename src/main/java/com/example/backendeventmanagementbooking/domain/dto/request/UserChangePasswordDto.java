package com.example.backendeventmanagementbooking.domain.dto.request;

public record UserChangePasswordDto(String oldPassword,
                                    String newPassword) {
}
