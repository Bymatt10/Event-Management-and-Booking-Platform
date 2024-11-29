package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventGuestEntity;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGuestRepository extends JpaRepository<EventGuestEntity, Long> {
    Boolean existsByEventAndUser(EventEntity event, UserEntity user);
    Boolean existsByEventAndUserAndInvitationStatus(EventEntity event, UserEntity user, InvitationStatusType invitationStatus);

    Integer countByEventAndInvitationStatus(EventEntity event, InvitationStatusType invitationStatus);

    EventGuestEntity findByEventAndUserAndInvitationStatus(EventEntity event, UserEntity user, InvitationStatusType invitationStatus);

}
