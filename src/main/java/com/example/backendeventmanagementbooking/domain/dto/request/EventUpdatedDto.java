package com.example.backendeventmanagementbooking.domain.dto.request;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventUpdatedDto {

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
    private List<String> categoriesUpdated;


}
