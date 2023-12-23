package com.sudosoo.takeiteasy.dto.category;

import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class CategoryResponseDto {

    private String categoryName;
    private Page<PostTitleDto> postResponseDtos;

    public CategoryResponseDto(String categoryName, Page<PostTitleDto> postTitleDtos) {
        this.categoryName = categoryName;
        this.postResponseDtos = postTitleDtos;
    }
}
