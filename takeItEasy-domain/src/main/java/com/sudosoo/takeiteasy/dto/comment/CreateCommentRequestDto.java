package com.sudosoo.takeiteasy.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateCommentRequestDto {
    @NotNull(message = "멤버아이디를 입력해 주세요.")
    private Long memberId;
    @NotNull(message = "게시글아이디를 입력해 주세요.")
    private Long postId;
    @NotNull(message = "내용을 입력해 주세요.")
    @Size(max = 500, message = "내용은 500자를 넘을 수 없습니다.")
    private String content;
}
