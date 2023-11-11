package com.sudosoo.takeiteasy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RelatedPostRequestDto {
    @NotNull(message = "게시글아이디를 입력해 주세요.")
    private Long postId;
    @NotNull(message = "연관관계 맺을 게시글아이디를 입력해 주세요.")
    private Long relatedPostId;
}
