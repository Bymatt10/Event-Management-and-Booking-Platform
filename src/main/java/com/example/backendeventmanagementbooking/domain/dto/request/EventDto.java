package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
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
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_FORMAT_DATE)
    private Date startDate;

    @NotBlankWithFieldName
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_FORMAT_DATE)
    private Date endDate;

    private String location;

    @NotBlankWithFieldName
    private int capacity;

    @NotBlankWithFieldName
    private double price;

    @NotBlankWithFieldName
    private String typeEvent;

    @NotBlankWithFieldName
    private List<String> categories;
}
