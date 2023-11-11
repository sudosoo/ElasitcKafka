package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;

public interface CategoryService {
    Category creatCategory(CreateCategoryRequestDto createCategoryRequestDto);
    Category getCategoryByCategoryId(Long categoryId);
}
