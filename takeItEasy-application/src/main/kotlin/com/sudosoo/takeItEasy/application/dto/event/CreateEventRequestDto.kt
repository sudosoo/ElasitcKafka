package com.sudosoo.takeItEasy.application.dto.event

import com.sudosoo.takeItEasy.application.annotation.CustomDateFormatValidator
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreateEventRequestDto(
    @field:NotBlank(message = "이벤트 이름을 입력해 주세요.") @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    val eventName: String?,
    @field:CustomDateFormatValidator
    val eventDeadline: String?,
    )
