package com.sudosoo.takeiteasy.dto;

import lombok.*;

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
