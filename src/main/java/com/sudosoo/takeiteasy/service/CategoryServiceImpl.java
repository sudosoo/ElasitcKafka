package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.SetCategoryByPostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
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
    private final PostService postService;

    public void creatCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        String categoryName = createCategoryRequestDto.getCategoryName();
        Category category = createCategoryEntityByCategoryName(categoryName);
        categoryRepository.save(category);
    }

    public void setCategorytByPost(SetCategoryByPostRequestDto setCategoryByPostRequestDto) {
        String categoryName = setCategoryByPostRequestDto.getCategoryName();
        Category category = categoryRepository.findByCategoryName(categoryName).orElseGet(
                ()-> createCategoryEntityByCategoryName(categoryName));
        Post post = postService.getPostByPostId(setCategoryByPostRequestDto.getPostId());

        category.addPost(post);
        categoryRepository.save(category);
    }

    public Category getCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Could not found category id : " + categoryId));
    }

    private Category createCategoryEntityByCategoryName(String categoryName) {
        return Category.buildEntityFromDto(categoryName);
    }

}
