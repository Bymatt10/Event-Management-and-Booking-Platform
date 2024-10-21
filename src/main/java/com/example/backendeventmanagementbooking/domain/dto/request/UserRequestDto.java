package com.example.backendeventmanagementbooking.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    @NotBlank(message = "The email can not  be null")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

}
