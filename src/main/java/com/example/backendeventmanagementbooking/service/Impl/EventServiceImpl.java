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
    public GenericResponse<EventDto> deleteEvent() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> updateEvent() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> findEventById() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> findAllEvents() {
        return null;
    }

    @Override
    public GenericResponse<EventDto> findAllEventsByUserId() {
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
