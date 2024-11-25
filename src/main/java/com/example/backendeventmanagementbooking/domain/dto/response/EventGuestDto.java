package com.example.backendeventmanagementbooking.domain.dto.response;

import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;
import lombok.Data;

@Data
public class EventGuestDto {
    private EventEntity event;
    private InvitationStatusType invitationStatus;
}
