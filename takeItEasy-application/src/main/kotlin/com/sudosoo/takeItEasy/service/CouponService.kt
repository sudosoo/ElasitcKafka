package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.event.CreateEventRequestDto
import com.sudosoo.takeiteasy.entity.Coupon

interface CouponService {
    fun priceCouponCreate(requestDto: CreateEventRequestDto): Coupon
    fun rateCouponCreate(requestDto: CreateEventRequestDto): Coupon
}
