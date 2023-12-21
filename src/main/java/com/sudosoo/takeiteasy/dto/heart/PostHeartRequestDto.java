package com.sudosoo.takeiteasy.dto.heart;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostHeartRequestDto {
    @NotNull(message = "유저아이디를 입력해 주세요.")
    private Long memberId;
    @NotNull(message = "게시글아이디를 입력해 주세요.")
    private Long postId;
}
