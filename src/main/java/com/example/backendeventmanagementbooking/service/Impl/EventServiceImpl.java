package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.request.EventUpdatedDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventGuestDto;
import com.example.backendeventmanagementbooking.domain.dto.response.EventResponseDto;
import com.example.backendeventmanagementbooking.domain.entity.CategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventGuestEntity;
import com.example.backendeventmanagementbooking.enums.StatusEvent;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.EventGuestRepository;
import com.example.backendeventmanagementbooking.repository.EventRepository;
import com.example.backendeventmanagementbooking.security.SecurityTools;
import com.example.backendeventmanagementbooking.service.CategoryService;
import com.example.backendeventmanagementbooking.service.EventGuestService;
import com.example.backendeventmanagementbooking.service.EventService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.example.backendeventmanagementbooking.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luigivismara.shortuuid.ShortUuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.DEFAULT_ALPHABET;
import static com.example.backendeventmanagementbooking.enums.EventAccessType.PUBLIC;
import static com.example.backendeventmanagementbooking.enums.InvitationStatusType.ACCEPTED;
import static com.example.backendeventmanagementbooking.utils.PaginationUtils.pageableRepositoryPaginationDto;

@Service

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService, EventGuestService {

    private final EventRepository eventRepository;
    private final EventGuestRepository eventGuestRepository;
    private final CategoryService categoryService;
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
    public GenericResponse<EventResponseDto> updateEvent(UUID uuid, EventUpdatedDto eventUpdatedDto) throws CustomException {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"))
                .fromEventUpdateDto(eventUpdatedDto, uuid);

        eventRepository.save(eventEntity);

        var listCategories = categoryService.updateCategoryNameByEvent(eventUpdatedDto.getCategoriesUpdated(), eventEntity);
        var response = objectMapper.convertValue(eventUpdatedDto, EventResponseDto.class);
        response.setCategories(listCategories);
        response.setUuid(uuid);

        return new GenericResponse<>(HttpStatus.OK, response);
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
        var dto = paginationFromEntityToEventResponseDto(response);
        return new GenericResponse<>(HttpStatus.OK, dto).GenerateResponse();
    }

    @Override
    public GenericResponse<EventDto> changeDate(UUID uuid, EventUpdatedDto eventUpdatedDto) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));

        eventEntity.setStartDate(eventUpdatedDto.getStartDate());
        eventEntity.setEndDate(eventUpdatedDto.getEndDate());
        return new GenericResponse<>(HttpStatus.OK, objectMapper.convertValue(eventEntity, EventDto.class));
    }

    @Override
    public GenericResponse<EventDto> changeLocation(UUID uuid, EventUpdatedDto eventUpdatedDto) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));

        eventEntity.setLocation(eventUpdatedDto.getLocation());
        return new GenericResponse<>(HttpStatus.OK, objectMapper.convertValue(eventEntity, EventDto.class));
    }

    @Override
    public GenericResponse<EventDto> changePrice(UUID uuid, EventUpdatedDto eventUpdatedDt) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));

        eventEntity.setPrice(eventUpdatedDt.getPrice());
        return new GenericResponse<>(HttpStatus.OK, objectMapper.convertValue(eventEntity, EventDto.class));
    }

    @Override
    public GenericResponse<EventDto> changeCapacity(UUID uuid, EventUpdatedDto eventUpdatedDt) {
        EventEntity eventEntity = eventRepository.findById(uuid)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Event not found"));

        eventEntity.setCapacity(eventUpdatedDt.getCapacity());
        return new GenericResponse<>(HttpStatus.OK, objectMapper.convertValue(eventEntity, EventDto.class));
    }

    @Override
    public GenericResponse<PaginationUtils.PaginationDto<EventResponseDto>> searchAllEventsPublic(PageRequest pageRequest) {
        var response = pageableRepositoryPaginationDto(eventRepository.findAllPublicEvents(pageRequest));
        var dto = paginationFromEntityToEventResponseDto(response);
        return new GenericResponse<>(HttpStatus.OK, dto);
    }

    //ToDo: remember count capacity of user already register

    @Override
    public GenericResponse<EventGuestDto> subscribeToPublicEvent(UUID eventUuid) {
        var user = securityTools.getCurrentUser();
        var event = eventRepository.findEventByUuidAndAccessTypeAndStartDateAfter(eventUuid, PUBLIC, new Date());
//        var event = eventRepository.findEventByUuidAndAccessType(eventUuid.toString(), PUBLIC);
        if (ObjectUtils.isEmpty(event)) return new GenericResponse<>(HttpStatus.NOT_FOUND);

        if (eventGuestRepository.existsByEventAndUser(event, user)) return new GenericResponse<>(HttpStatus.CONFLICT);

        var totalParticipant = eventGuestRepository.countByEventAndInvitationStatus(event, ACCEPTED);
        if (event.getCapacity() < totalParticipant) {
            return new GenericResponse<>("Capacity of event reached max", HttpStatus.NOT_ACCEPTABLE);
        }

        var eventGuest = new EventGuestEntity(
                event,
                user,
                PUBLIC,
                ShortUuid.encode(UUID.randomUUID(), DEFAULT_ALPHABET, 5).toString(),
                ACCEPTED);
        var saved = eventGuestRepository.save(eventGuest);
        var response = new EventGuestDto(saved, ACCEPTED);
        return new GenericResponse<>(HttpStatus.OK, response);
    }

    @Override
    public GenericResponse<Void> unsubscribeFromPublicEvent(UUID eventUuid) {
        return null;
    }

    @Override
    public GenericResponse<Void> inviteToPrivateEvent(UUID eventUuid, UUID userId) {
        return null;
    }

    @Override
    public GenericResponse<EventGuestDto> subscribeToPrivateEvent(UUID eventUuid) {
        return null;
    }

    @Override
    public GenericResponse<Void> unsubscribeFromPrivateEvent(UUID eventUuid) {
        return null;
    }

    private PaginationUtils.PaginationDto<EventResponseDto> paginationFromEntityToEventResponseDto(PaginationUtils.PaginationDto<EventEntity> response) {
        var transformation = response.getValue().stream().map(event -> {
            var res = objectMapper.convertValue(event, EventResponseDto.class);
            res.setCategories(categoryService.getCategoryNameByEvent(event));
            return res;
        }).toList();

        return new PaginationUtils.PaginationDto<>(transformation,
                response.getCurrentPage(),
                response.getTotalPages(),
                response.getTotalItems()
        );
    }
}
