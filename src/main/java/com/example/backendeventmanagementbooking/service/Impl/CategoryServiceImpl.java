package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.entity.CategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventCategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.repository.CategoryRepository;
import com.example.backendeventmanagementbooking.repository.EventCategoryRepository;
import com.example.backendeventmanagementbooking.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventCategoryRepository eventCategoryRepository;

    @Override
    public List<CategoryEntity> saveOrGetCategoryList(List<String> categoryNameList, EventEntity savedEvent) {

        var list = new ArrayList<CategoryEntity>();

        for (String categoryName : categoryNameList) {
            var category = categoryRepository.findByName(categoryName);
            if (category.isPresent()) {
                list.add(category.get());
            }

            if (category.isEmpty()) {
                list.add(categoryRepository.save(CategoryEntity.builder()
                        .name(categoryName)
                        .build()));
            }

        }

        list.stream().parallel()
                .forEach(categoryEntity -> eventCategoryRepository.save(EventCategoryEntity.builder()
                        .category(categoryEntity)
                        .event(savedEvent)
                        .build()));

        return list;
    }

}
