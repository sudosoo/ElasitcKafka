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
    public void creatCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        String categoryName = createCategoryRequestDto.getCategoryName();
        Category category = createCategoryEntityByCategoryName(categoryName);

        categoryRepository.save(category);
        log.info("New Category created :  categoryId {}", category.getId());
    }

    public Category getCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Could not found category id : " + categoryId));
    }

    @Override
    public Category createCategoryEntityByCategoryName(String categoryName) {
        return new Category(categoryName);
    }

    @Override
    public Category findByCategoryName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new IllegalArgumentException("Could not found category name : " + categoryName));
    }
}
