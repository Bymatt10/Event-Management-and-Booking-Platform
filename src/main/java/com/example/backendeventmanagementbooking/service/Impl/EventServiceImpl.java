package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.request.EventUpdatedDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.CategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.enums.StatusEvent;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.CategoryRepository;
import com.example.backendeventmanagementbooking.repository.EventRepository;
import com.example.backendeventmanagementbooking.security.SecurityTools;
import com.example.backendeventmanagementbooking.service.CategoryService;
import com.example.backendeventmanagementbooking.service.EventService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.example.backendeventmanagementbooking.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backendeventmanagementbooking.utils.PaginationUtils.pageableRepositoryPaginationDto;

@Service

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final SecurityTools securityTools;

    @Override
    public GenericResponse<EventResponseDto> saveEvent(EventDto eventDto) {
        var user = securityTools.getCurrentUser();
        var eventEntity = new EventEntity(eventDto, user);
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
    public GenericResponse<Object> deleteEvent(UUID uuid) {
        var eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));
        eventEntity.setStatus(StatusEvent.INACTIVE);
        var user = securityTools.getCurrentUser();
        if (!user.getUsername().equals(eventEntity.getUser().getUsername())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You do not have permission to delete this event");
        }

        eventRepository.save(eventEntity);
        return new GenericResponse<>(HttpStatus.OK);
    }

    @Override
    public GenericResponse<EventUpdatedDto> updateEvent(UUID uuid, EventUpdatedDto eventUpdatedDto) throws CustomException {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));

        eventEntity.setTitle(eventUpdatedDto.getTitle());
        eventEntity.setDescription(eventUpdatedDto.getDescription());
        eventEntity.setPathImage(eventUpdatedDto.getPathImage());
        eventEntity.setStartDate(eventUpdatedDto.getStartDate());
        eventEntity.setEndDate(eventUpdatedDto.getEndDate());
        eventEntity.setLocation(eventUpdatedDto.getLocation());  // Removed duplicate
        eventEntity.setCapacity(eventUpdatedDto.getCapacity());
        eventEntity.setPrice(eventUpdatedDto.getPrice());
        eventEntity.setTypeEvent(eventUpdatedDto.getTypeEvent());
        eventEntity.setStatus(StatusEvent.ACTIVE);

        List<CategoryEntity> updatedCategories = new ArrayList<>();
        for (EventUpdatedDto.CategoriesUpdated categoriesUpdatedDto : eventUpdatedDto.getCategoriesUpdated()) {
            CategoryEntity categoryEntity = categoryRepository.findById(categoriesUpdatedDto.getUuidCategory())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found"));

            categoryEntity.setName(categoriesUpdatedDto.getName());
            categoryService.saveOrGetCategoryList(categoryEntity);
            updatedCategories.add(categoryEntity);
        }
//
//        eventEntity.setCategories(updatedCategories);
//        eventRepository.save(eventEntity);

        return new GenericResponse<>(HttpStatus.OK, eventUpdatedDto);
    }


    @Override
    public GenericResponse<EventResponseDto> findEventById(UUID uuid) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found with UUID: " + uuid));

        var categories = categoryService.getCategoryNameByEvent(eventEntity);
        EventResponseDto response = objectMapper.convertValue(eventEntity, EventResponseDto.class);
        response.setCategories(categories);
        return new GenericResponse<>(HttpStatus.OK, response);
    }


    @Override
    public ResponseEntity<GenericResponse<PaginationUtils.PaginationDto<EventResponseDto>>> findAllEvents(PageRequest pageRequest) {
        var user = securityTools.getCurrentUser();
        var response = pageableRepositoryPaginationDto(eventRepository.findAllByUser(pageRequest, user));
        var transformation = response.getValue().stream().map(event -> {
            var res = objectMapper.convertValue(event, EventResponseDto.class);
            res.setCategories(categoryService.getCategoryNameByEvent(event));
            return res;
        }).toList();

        var dto = new PaginationUtils.PaginationDto<>(transformation,
                response.getCurrentPage(),
                response.getTotalPages(),
                response.getTotalItems()
        );
        return new GenericResponse<>(HttpStatus.OK, dto).GenerateResponse();
    }


    @Override
    public GenericResponse findAllEventsByUserId(UUID userId) {
        var user = securityTools.getCurrentUser();
        EventEntity eventEntity = eventRepository.findAllEventsByUserId(user.getUserId());
        return objectMapper.convertValue(eventEntity, GenericResponse.class);

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
