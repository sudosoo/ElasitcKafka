package com.sudosoo.takeItEasy.dto.heart

import jakarta.validation.constraints.NotBlank

class CommentHeartRequestDto(
    @NotBlank(message = "멤버아이디를 입력해 주세요.")
    val memberId: Long,
    @NotBlank(message = "댓글아이디를 입력해 주세요.")
    val commentId: Long
)