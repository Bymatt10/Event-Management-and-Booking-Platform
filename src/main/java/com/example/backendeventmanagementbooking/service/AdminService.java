package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.response.EventGuestDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

public interface AdminService {
    GenericResponse<EventGuestDto> verifyAccessToEvent(String securityCode);
}
