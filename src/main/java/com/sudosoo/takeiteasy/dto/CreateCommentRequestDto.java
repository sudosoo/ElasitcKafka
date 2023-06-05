package com.sudosoo.takeiteasy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentRequestDto {
    private Long memberId;
    private Long postId;
    private String content;


}
