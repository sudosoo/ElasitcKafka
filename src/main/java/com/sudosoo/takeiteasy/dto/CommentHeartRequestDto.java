package com.sudosoo.takeiteasy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentHeartRequestDto {
    @NotNull(message = "멤버아이디를 입력해 주세요.")
    private Long memberId;
    @NotNull(message = "댓글아이디를를 입력해 주세요.")
    private Long commentId;
}
