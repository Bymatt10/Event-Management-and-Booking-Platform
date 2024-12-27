package com.example.backendeventmanagementbooking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Configuration
public class PaypalConfig {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.sandbox}")
    private Boolean sandbox;

    @Bean
    public PayPalEnvironment payPalEnvironment() {
        return (sandbox ? 
        new PayPalEnvironment.Sandbox(clientId, clientSecret) : 
        new PayPalEnvironment.Live(clientId, clientSecret));
    }

    @Bean
    public PayPalHttpClient payPalHttpClient(PayPalEnvironment environment) {
        return new PayPalHttpClient(environment);
    }
}
