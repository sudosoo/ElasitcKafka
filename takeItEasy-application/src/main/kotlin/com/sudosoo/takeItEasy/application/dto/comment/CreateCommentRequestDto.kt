package com.sudosoo.takeItEasy.application.dto.comment

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
class CreateCommentRequestDto (
    @NotBlank(message = "멤버아이디를 입력해 주세요.")
    val memberId: Long,
    @NotBlank(message = "게시글아이디를 입력해 주세요.")
    val postId: Long,
    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(max = 500, message = "내용은 500자를 넘을 수 없습니다.")
    val content: String
)
