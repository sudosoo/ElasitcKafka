package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

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

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getPostsByCategoryId(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다"));

        Page<Post> paginatedPost = postRepository.findPostsPaginationByCategoryId(categoryId, pageable);
        List<PostTitleDto> responsePosts = paginatedPost.stream().map(Post::toTitleOnlyDto).toList();

        return category.toResponseDto(category, new PageImpl<>(responsePosts));

    }

}
