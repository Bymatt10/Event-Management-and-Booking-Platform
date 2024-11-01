package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {
    GenericResponse<EventResponseDto> saveEvent(EventDto eventDto);

    GenericResponse<EventDto> deleteEvent(UUID uuid);

    GenericResponse<EventDto> updateEvent();

    GenericResponse<EventResponseDto> findEventById(UUID uuid);

    GenericResponse<List<EventResponseDto>>findAllEvents();

    GenericResponse<EventDto> findAllEventsByUserId(UUID userId);

    GenericResponse<EventDto> changeDate();

    GenericResponse<EventDto> changeLocation();

    GenericResponse<EventDto> changePrice();

    GenericResponse<EventDto> changeCapacity();

}
