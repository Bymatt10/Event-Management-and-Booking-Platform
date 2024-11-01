package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.CategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.EventRepository;
import com.example.backendeventmanagementbooking.service.CategoryService;
import com.example.backendeventmanagementbooking.service.EventService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @Override
    public GenericResponse<EventResponseDto> saveEvent(EventDto eventDto) {
        var eventEntity = new EventEntity(eventDto);
        if (eventDto.getEndDate().before(eventEntity.getStartDate())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "End date cannot be before start date");
        }
        var savedEvent = eventRepository.save(eventEntity);

        var categories = categoryService.saveOrGetCategoryList(eventDto.getCategories(), savedEvent)
                .stream()
                .map(CategoryEntity::getName)
                .toList();

        var response = objectMapper.convertValue(savedEvent, EventResponseDto.class);
        response.setCategories(categories);
        return new GenericResponse<>(HttpStatus.OK, response);
    }


    @Override
    public GenericResponse<EventDto> deleteEvent(UUID uuid) {
        var eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));
        eventRepository.delete(eventEntity);
        return new GenericResponse<>(HttpStatus.OK, null);
    }

    @Override
    public GenericResponse<EventDto> updateEvent() {
        return null;
    }

    @Override
    public GenericResponse<EventResponseDto> findEventById(UUID uuid) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found with UUID: " + uuid));

        EventResponseDto response = objectMapper.convertValue(eventEntity, EventResponseDto.class);
        return new GenericResponse<>(HttpStatus.OK, response);
    }


    @Override
    public GenericResponse<List<EventResponseDto>> findAllEvents() {
        List<EventEntity> events = eventRepository.findAll();
        List<EventResponseDto> eventDto = events.stream()
                .map(event -> objectMapper.convertValue(event, EventResponseDto.class))
                .collect(Collectors.toList());
        return new GenericResponse<>(HttpStatus.OK, eventDto);
    }


    @Override
    public GenericResponse<EventDto> findAllEventsByUserId(UUID userId) {
        return null;
    }

    @Override
    public GenericResponse<EventDto> changeDate() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> changeLocation() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> changePrice() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> changeCapacity() {
        return null;
    }
}
