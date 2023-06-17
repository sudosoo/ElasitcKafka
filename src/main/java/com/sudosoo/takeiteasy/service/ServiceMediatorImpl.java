package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.SetCategoryByPostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceMediatorImpl implements ServiceMediator {
    private final PostService postService;
    private final CategoryService categoryService;

    public void setCategoryByPost(SetCategoryByPostRequestDto setCategoryByPostRequestDto) {
        String categoryName = setCategoryByPostRequestDto.getCategoryName();
        Category category = categoryService.findByCategoryName(categoryName);
        Post post = postService.getPostByPostId(setCategoryByPostRequestDto.getPostId());

        category.addPost(post);
        categoryService.saveCategory(category);
        log.info("Set Category add Post : categoryName {}, postId{}", category.getCategoryName(),post.getId());
    }

    public Category getCategoryByCategoryId (Long categoryId){
        return categoryService.getCategoryByCategoryId(categoryId);
    }


}
