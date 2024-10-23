package com.example.backendeventmanagementbooking.utils;

import com.example.backendeventmanagementbooking.domain.dto.request.EmailDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;


    @Async("email")
    public void sendMail(EmailDetailsDto emailDetailsDto) {
        log.info("Sending email to:{}", emailDetailsDto.recipient());
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetailsDto.recipient());
        mailMessage.setSubject(emailDetailsDto.subject());
        mailMessage.setText(emailDetailsDto.msgBody());
        mailSender.send(mailMessage);
        log.info("Email sent successfully");
    }


}
