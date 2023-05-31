package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.SetCategoryByPostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;

public interface CategoryService {
    void creatCategory(CreateCategoryRequestDto createCategoryRequestDto);
    void setCategorytByPost(SetCategoryByPostRequestDto setCategoryByPostRequestDto);
    Category getCategoryByCategoryId(Long categoryId);
}
