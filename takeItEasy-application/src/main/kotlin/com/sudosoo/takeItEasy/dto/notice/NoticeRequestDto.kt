package com.sudosoo.takeItEasy.dto.notice

import jakarta.validation.constraints.NotBlank

class NoticeRequestDto(
    @NotBlank(message = "내용을 입력해 주세요.")
    val content:String )
