package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category creatCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = Category.of(createCategoryRequestDto);

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Could not found category id : " + categoryId));
    }

}
