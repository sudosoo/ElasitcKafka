package com.sudosoo.takeItEasy.application.dto.notice

import jakarta.validation.constraints.NotNull

class NoticeResponseDto(
    @NotNull(message = "내용을 입력해 주세요.")
    val content : String ,
    val isRead : Boolean = false) {
}
