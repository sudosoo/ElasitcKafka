package com.sudosoo.takeiteasy.dto.category;

import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponseDto {

    private String categoryName;
    private Page<PostTitleDto> postResponseDtos;

    public CategoryResponseDto(String categoryName, Page<PostTitleDto> postTitleDtos) {
        this.categoryName = categoryName;
        this.postResponseDtos = postTitleDtos;
    }
}
