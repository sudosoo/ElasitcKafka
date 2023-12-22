package com.sudosoo.takeiteasy.dto.category;

import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryResponseDto {

    private Long categoryId;
    private Page<PostTitleDto> postResponseDtos;

    public CategoryResponseDto(Long categoryId, Page<PostTitleDto> postTitleDtos) {
        this.categoryId = categoryId;
        this.postResponseDtos = postTitleDtos;
    }
}
