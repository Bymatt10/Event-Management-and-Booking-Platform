package com.example.backendeventmanagementbooking.utils;

import com.example.backendeventmanagementbooking.domain.dto.request.EmailDetailsDto;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
    public void sendMail(EmailDetailsDto emailDetailsDto) throws MessagingException {
        log.info("Sending email to:{}", emailDetailsDto.recipient());

        var mimeMessage = mailSender.createMimeMessage();
        var mailMessage = new MimeMessageHelper(mimeMessage, true);

        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetailsDto.recipient());
        mailMessage.setSubject(emailDetailsDto.subject());
        mailMessage.setText(emailDetailsDto.msgBody(), true);

        mailSender.send(mimeMessage);

        log.info("Email sent successfully");
    }
}
