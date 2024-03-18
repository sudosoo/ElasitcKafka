package com.sudosoo.takeItEasy.dto.heart

import jakarta.validation.constraints.NotBlank
class PostHeartRequestDto(
    @NotBlank(message = "유저아이디를 입력해 주세요.")
    val memberId: Long,
    @NotBlank(message = "게시글아이디를 입력해 주세요.")
    val postId: Long
)