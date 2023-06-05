package com.sudosoo.takeiteasy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequestDto {
    private String title;
    private String content;
    private Long memberId;
    @Nullable
    private Long categoryId;
    @Nullable
    private Long relatedPostId;
}
