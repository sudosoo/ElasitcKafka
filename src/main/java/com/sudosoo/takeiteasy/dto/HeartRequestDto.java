package com.sudosoo.takeiteasy.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {

    private Long memberId;
    private Long postId;
    private Long commentId;

    @Builder
    private HeartRequestDto(Long memberId, Long postId, Long commentId) {
        this.memberId = memberId;
        this.postId = postId;
        this.commentId = commentId;
    }
}
