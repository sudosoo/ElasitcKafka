package com.sudosoo.takeiteasy.service;


import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public CategoryResponseDto getPostsByCategoryId(Long categoryId, PageRequest pageRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        List<Post> posts = category.getPosts();
        List<PostTitleDto> postList = posts.stream().map(Post::toTitleOnlyDto).toList();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), postList.size());
        Page<PostTitleDto> paginatedPost = new PageImpl<>(postList.subList(start, end), pageRequest, postList.size());

        return new CategoryResponseDto(categoryId,paginatedPost);

    }

}
