package com.sudosoo.takeiteasy.dto.heart;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentHeartRequestDto {
    @NotNull(message = "유저아이디를 입력해 주세요.")
    private Long memberId;
    @NotNull(message = "댓글아이디를 입력해 주세요.")
    private Long commentId;
}
