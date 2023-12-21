package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import com.sudosoo.takeiteasy.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    Category creatCategory(CreateCategoryRequestDto createCategoryRequestDto);
    Category getCategoryByCategoryId(Long categoryId);
    CategoryResponseDto getPostsByCategoryId(Long categoryId, PageRequest pageRequest);
}
