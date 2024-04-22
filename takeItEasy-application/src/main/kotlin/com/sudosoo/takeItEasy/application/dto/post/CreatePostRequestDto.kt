package com.sudosoo.takeItEasy.application.dto.post

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreatePostRequestDto(
    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    val title: String,
    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(max = 500, message = "내용은 500자를 넘을 수 없습니다.")
    val content: String,
    @NotBlank(message = "멤버아이디를 입력해 주세요.")
    val memberId: Long,
    @NotBlank(message = "작성자를 입력해 주세요.")
    val writerName: String,
    @NotBlank(message = "카테고리아이디를 입력해 주세요.")
    val categoryId: Long
)
