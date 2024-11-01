package com.example.backendeventmanagementbooking.domain.dto.response;


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
    private List<String> categories;
}                                         
