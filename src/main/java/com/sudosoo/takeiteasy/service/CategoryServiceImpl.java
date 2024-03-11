package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService , JpaService<Category, Long> {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Override
    public JpaRepository<Category, Long> getJpaRepository() {
        return categoryRepository;
    }

    @Override
    public Category createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = Category.of(createCategoryRequestDto);
        return saveModel(category);
    }

    @Override
    public Category getById(Long categoryId) {
        return findModelById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getPostsByCategoryId(Long categoryId, Pageable pageable) {
        Category category = findModelById(categoryId);

        Page<Post> paginatedPost = postRepository.findPostsPaginationByCategoryId(categoryId, pageable);
        List<PostTitleOnlyResponseDto> responsePosts = paginatedPost.stream().map(Post::toTitleOnlyDto).toList();

        return category.toResponseDto(category, new PageImpl<>(responsePosts));

    }

}
