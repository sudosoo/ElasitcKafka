package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.domain.entity.Coupon

interface CouponService {
    fun priceCouponCreate(requestDto: CreateEventRequestDto): Coupon
    fun rateCouponCreate(requestDto: CreateEventRequestDto): Coupon
}
