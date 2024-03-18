package com.sudosoo.takeItEasy.dto.post

import jakarta.validation.constraints.NotBlank

class SetCategoryByPostRequestDto(
    @NotBlank(message = "카테고리아이디를 입력해 주세요.")
    val categoryId: Long,
    @NotBlank(message = "게시글아이디를 입력해 주세요.")
    val postId: Long)
