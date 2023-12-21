package com.sudosoo.takeiteasy.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetCategoryByPostRequestDto {
    @NotNull(message = "카테고리아이디를 입력해 주세요.")
    private Long categoryId ;
    @NotNull(message = "게시글아이디를 입력해 주세요.")
    private Long postId;
}
