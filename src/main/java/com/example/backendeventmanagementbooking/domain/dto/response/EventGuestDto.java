package com.example.backendeventmanagementbooking.domain.dto.response;

import com.example.backendeventmanagementbooking.domain.entity.EventGuestEntity;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;

public record EventGuestDto(EventGuestEntity event,
                            InvitationStatusType invitationStatus) {
}
