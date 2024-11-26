package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.enums.EventAccessType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventDto {
    @NotBlankWithFieldName
    private String title;

    @NotBlankWithFieldName
    private String description;

    private String pathImage;

    @NotBlankWithFieldName
    private Date startDate;

    @NotBlankWithFieldName
    private Date endDate;

    private String location;

    @NotBlankWithFieldName
    private int capacity;

    @NotBlankWithFieldName
    private double price;

    @NotBlankWithFieldName
    private String typeEvent;

    @NotBlankWithFieldName
    private EventAccessType accessType;

    @NotBlankWithFieldName
    private List<String> categories;
}
