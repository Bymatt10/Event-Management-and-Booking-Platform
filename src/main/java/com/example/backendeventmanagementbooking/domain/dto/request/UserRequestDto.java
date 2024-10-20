package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.enums.RolesType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private RolesType role;
}
