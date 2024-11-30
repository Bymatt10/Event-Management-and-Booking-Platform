package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.common.PrivateEventInvitationDto;
import com.example.backendeventmanagementbooking.domain.entity.EventGuestEntity;
import com.example.backendeventmanagementbooking.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventCacheService {

    @CachePut(value = "send-private-invitation", key = "#eventGuest.verificationCode")
    public PrivateEventInvitationDto saveInvitationToRedis(EventGuestEntity eventGuest) {
        return new PrivateEventInvitationDto(
                eventGuest.getEvent().getUuid(),
                eventGuest.getVerificationCode(),
                null
        );
    }

    @Cacheable(value = "send-private-invitation", key = "#verificationCode")
    public PrivateEventInvitationDto getInvitationFromRedis(String verificationCode) {
        throw new CustomException(HttpStatus.UNAUTHORIZED, "No cache entry found for verification code: " + verificationCode);
    }

    @CacheEvict(value = "send-private-invitation", key = "#verificationCode")
    public void evictInvitationFromRedis(String verificationCode) {
        log.info("Cache entry for key '{}' evicted successfully", verificationCode);
    }
}