package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.response.EventGuestDto;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.EventGuestRepository;
import com.example.backendeventmanagementbooking.service.AdminService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.backendeventmanagementbooking.enums.InvitationStatusType.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {

    private final EventGuestRepository eventGuestRepository;

    @Override
    public GenericResponse<EventGuestDto> verifyAccessToEvent(String securityCode) {
        var event = eventGuestRepository.findByVerificationCode(securityCode);
        if (event == null) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Access denied for this security code");
        }

        if (event.getInvitationStatus() == PENDING) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Participation isn't confirmed");
        }

        return new GenericResponse<>(HttpStatus.OK, new EventGuestDto(event, event.getInvitationStatus()));
    }
}
