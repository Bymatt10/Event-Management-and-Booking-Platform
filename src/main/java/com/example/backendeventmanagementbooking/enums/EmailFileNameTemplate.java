package com.example.backendeventmanagementbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailFileNameTemplate {
    REGISTER_USER("register_email.html"),
    LOGIN_USER("login_email.html");

    private final String value;
}
