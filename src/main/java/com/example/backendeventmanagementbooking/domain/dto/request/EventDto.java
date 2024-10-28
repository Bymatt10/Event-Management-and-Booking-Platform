package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    @NotBlankWithFieldName
    private String title;

    @NotBlankWithFieldName
    private String description;

    private String pathImage;

    @NotBlankWithFieldName
    private Long idCategory;

    @NotBlankWithFieldName
    private LocalDateTime startDate;

    @NotBlankWithFieldName
    private LocalDateTime endDate;

    private String location;

    @NotBlankWithFieldName
    private int capacity;

    @NotBlankWithFieldName
    private double price;

    @NotBlankWithFieldName
    private String typeEvent;
}
