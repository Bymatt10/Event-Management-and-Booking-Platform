package com.example.backendeventmanagementbooking.domain.dto.common;

import java.io.Serializable;
import java.util.UUID;

public record PrivateEventInvitationDto(UUID eventUuid,
                                       String securityCode,
                                       Object metadata) implements Serializable {
}
