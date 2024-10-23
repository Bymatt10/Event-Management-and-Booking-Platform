package com.example.backendeventmanagementbooking.domain.dto.request;

public record EmailDetailsDto(String recipient,
                              String msgBody,
                              String subject) {

}
