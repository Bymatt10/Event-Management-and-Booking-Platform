package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.service.EventService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/event/")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("create")
    public ResponseEntity<GenericResponse<EventResponseDto>> saveEvent(@RequestBody @Valid  EventDto eventDto) {
        return eventService.saveEvent(eventDto).GenerateResponse();
    }
}
