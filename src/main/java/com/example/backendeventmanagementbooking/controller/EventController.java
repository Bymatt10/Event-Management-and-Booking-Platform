package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.service.EventService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/event/")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("create")
    public ResponseEntity<GenericResponse<EventResponseDto>> saveEvent(@RequestBody @Valid EventDto eventDto) {
        return eventService.saveEvent(eventDto).GenerateResponse();
    }

    @GetMapping("list")
    public ResponseEntity<GenericResponse<List<EventResponseDto>>> getAllEvents() {
        return eventService.findAllEvents().GenerateResponse();
    }

    @GetMapping("list/{uuid}")
    public ResponseEntity<GenericResponse<EventResponseDto>> findEventById(@PathVariable UUID uuid) {
        return eventService.findEventById(uuid).GenerateResponse();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<GenericResponse<EventDto>> deleteEvent(@PathVariable UUID uuid) {
        return eventService.deleteEvent(uuid).GenerateResponse();
    }
}
