package com.example.backendeventmanagementbooking.security;

import com.example.backendeventmanagementbooking.domain.entity.PaymentCardEntity;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.PaymentCardRepository;
import com.example.backendeventmanagementbooking.repository.UserRepository;
import com.example.backendeventmanagementbooking.utils.GeoLocationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityTools {

    private final UserRepository userRepository;
    private final PaymentCardRepository paymentCardRepository;
    private final GeoLocationService geoLocationService;

    public UserEntity getCurrentUser() {
        var username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public PaymentCardEntity getCurrentPaymentCard() {
        var profile = getCurrentUser().getProfile();
        var paymentCard = paymentCardRepository.findByProfileAndDefaultCardIsTrue(profile);

        if (paymentCard == null) {
            throw new CustomException(HttpStatus.CONFLICT, "There is no payment card associated with this profile");
        }
        return paymentCard;
    }

    public String getCurrentCountryCode() {
        var request = getCurrentHttpRequest();
        if (request != null) {
            return geoLocationService.getCountryCode(request);
        }
        return "US";
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }
}
