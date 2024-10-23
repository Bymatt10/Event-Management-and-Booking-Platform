package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.dto.request.EmailDetailsDto;

public interface EmailService {
    String sendSimpleMail(EmailDetailsDto details);

    String sendMailWithHtml(EmailDetailsDto details);
}
