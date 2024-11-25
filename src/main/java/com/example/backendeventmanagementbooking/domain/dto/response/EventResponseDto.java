package com.example.backendeventmanagementbooking.domain.dto.response;


import com.example.backendeventmanagementbooking.enums.EventAccessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
    private UUID uuid;
    private String title;
    private String description;
    private String pathImage;
    private String capacity;
    private String price;
    private String typeEvent;
    private EventAccessType accessType;
    private List<String> categories;
}                                         
