package com.sudosoo.takeItEasy.application.service.coupon

import com.sudosoo.takeItEasy.application.dto.coupon.CouponWrapperCreateDto

interface CouponWrapperService {
    fun create(requestDto: CouponWrapperCreateDto)
}
