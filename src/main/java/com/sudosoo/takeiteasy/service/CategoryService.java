package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    Category createCategory(CreateCategoryRequestDto createCategoryRequestDto);
    Category getCategoryByCategoryId(Long categoryId);
    CategoryResponseDto getPostsByCategoryId(Long categoryId, Pageable pageable);
}
