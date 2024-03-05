package com.sudosoo.takeiteasy.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private String memberName;
    private Long categoryId;
}
