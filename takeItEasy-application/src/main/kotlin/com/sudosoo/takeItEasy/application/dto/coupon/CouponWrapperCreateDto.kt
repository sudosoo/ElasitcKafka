package com.sudosoo.takeItEasy.application.dto.coupon

import com.sudosoo.takeItEasy.application.annotation.CustomDateFormatValidator
import jakarta.validation.constraints.Size

class CouponWrapperCreateDto(
    val eventId: Long,
    @CustomDateFormatValidator
    val couponDeadline: String,
    val couponQuantity: Int,
    val discountPrice: Long?,
    @Size(max = 100, message = "할인율은 100을 넘을 수 없습니다.")
    val discountRate: Int?,
    var eventName:String?
)
