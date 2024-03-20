package com.sudosoo.takeItEasy.application.dto.event

import com.sudosoo.takeItEasy.application.annotation.CustomDateFormatValidator
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreateEventRequestDto(
    @NotBlank(message = "제목을 입력해 주세요.") @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    val eventName: String,
    @CustomDateFormatValidator
    val eventDeadline: String,
    @CustomDateFormatValidator
    val couponDeadline: String,
    val couponQuantity: Int,
    val discountPrice: Long? = 0,
    @Size(max = 100, message = "할인율은 100을 넘을 수 없습니다.")
    val discountRate: Int? = 0
)
