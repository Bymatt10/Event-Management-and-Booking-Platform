package com.example.backendeventmanagementbooking.domain.dto.response;

import com.example.backendeventmanagementbooking.enums.RolesType;

import java.util.UUID;

public record UserMeResponseDto(UUID uuid,
                                String username,
                                String email,
                                RolesType role) {}