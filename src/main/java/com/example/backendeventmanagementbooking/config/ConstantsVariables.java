package com.example.backendeventmanagementbooking.config;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class ConstantsVariables {
    //https://developers.thoughtspot.com/docs/v1v2-comparison
    public static final String API_V1 = "/api/v1";
    public static final String API_V2 = "/api/v2";

    public static final String APPLICATION_VERSION = "1.0";

    public static final String SERVER_CONNECTION_TIMEOUT = "5000";

    public static final String JSON_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_SECURE_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String PATTERN_PHONE_NUMBER_NIC = "^(2|5|7|8)\\d{7}$";
    public static final String PATTERN_SECURE_PASSWORD_MESSAGE = "The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@, $, !, %, *, ?, &).";
    public static final String PATTERN_PHONE_NUMBER_NIC_MESSAGE = "Please provide a valid phone number";
    public static final String INVALID_STRING_NOT_BLANK = " is required and cannot be blank.";
    public static final String INVALID_EMAIL = "Please provide a valid email address.";
    public static final char[] DEFAULT_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    public static final char[] DEFAULT_ALPHABET_UPPER = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();

    public static final DateTimeFormatter EXPIRED_CREDIT_CARD_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();
}