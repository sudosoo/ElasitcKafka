package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;

public interface CategoryService {
    void creatCategory(CreateCategoryRequestDto createCategoryRequestDto);
    Category getCategoryByCategoryId(Long categoryId);
    Category createCategoryEntityByCategoryName(String categoryName);
    Category findByCategoryName(String categoryName);
}
