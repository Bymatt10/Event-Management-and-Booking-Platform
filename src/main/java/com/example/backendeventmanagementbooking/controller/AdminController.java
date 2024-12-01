package com.example.backendeventmanagementbooking.controller;


import com.example.backendeventmanagementbooking.domain.dto.response.EventGuestDto;
import com.example.backendeventmanagementbooking.service.AdminService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/admin/")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("event/verify/identity")
    public ResponseEntity<GenericResponse<EventGuestDto>> verifyAccessToEvent(@RequestParam String securityCode) {
        log.info("verifyAccessToEvent {}", securityCode);
        return adminService.verifyAccessToEvent(securityCode).GenerateResponse();
    }
}

