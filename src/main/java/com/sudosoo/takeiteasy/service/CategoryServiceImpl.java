package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public void creatCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = Category.buildEntityFromDto(createCategoryRequestDto.getCategoryName());
        categoryRepository.save(category);
    }

    public Category getCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Could not found category id : " + categoryId));
    }

}
