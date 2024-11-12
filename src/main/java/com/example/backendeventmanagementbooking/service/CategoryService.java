package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.entity.CategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> saveOrGetCategoryList(List<String> categoryNameList, EventEntity savedEvent);

    List<String> getCategoryNameByEvent(EventEntity savedEvent);

}
