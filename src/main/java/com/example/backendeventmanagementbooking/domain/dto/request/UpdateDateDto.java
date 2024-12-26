package com.example.backendeventmanagementbooking.domain.dto.request;

import java.time.LocalDate;
import java.util.Date;

public record UpdateDateDto(Date startDate, Date endDate) {
}

