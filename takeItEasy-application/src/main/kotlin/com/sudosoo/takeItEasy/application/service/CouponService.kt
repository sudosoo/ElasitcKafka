package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto

interface CouponService {

    fun create(requestDto: CreateEventRequestDto)

    fun couponIssuance(requestDto: CouponIssuanceRequestDto)

}
