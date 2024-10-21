package com.example.backendeventmanagementbooking.domain.dto.response;

public record UserMeResponseDto(String uuid,
                                String username,
                                String email,
                                String role) {}