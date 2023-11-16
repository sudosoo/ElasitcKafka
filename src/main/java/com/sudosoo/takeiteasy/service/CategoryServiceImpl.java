package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
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

    public Category getCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Could not found category id : " + categoryId));
    }

}
