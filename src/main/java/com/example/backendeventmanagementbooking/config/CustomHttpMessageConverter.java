package com.example.backendeventmanagementbooking.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

@Slf4j
@Configuration
public class CustomHttpMessageConverter {
    @Bean
    public HttpMessageConverter<BufferedImage> customConverters() {
        return new BufferedImageHttpMessageConverter();
    }
}
