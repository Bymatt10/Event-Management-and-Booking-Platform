package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

public interface EventService {
    GenericResponse<EventResponseDto> saveEvent(EventDto eventDto);

    GenericResponse<EventDto> deleteEvent();

    GenericResponse<EventDto> updateEvent();

    GenericResponse<EventDto> findEventById();

    GenericResponse<EventDto> findAllEvents();

    GenericResponse<EventDto> findAllEventsByUserId();

    GenericResponse<EventDto> changeDate();

    GenericResponse<EventDto> changeLocation();

    GenericResponse<EventDto> changePrice();

    GenericResponse<EventDto> changeCapacity();

}
