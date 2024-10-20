package com.example.backendeventmanagementbooking.domain.dto.request;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;
}
