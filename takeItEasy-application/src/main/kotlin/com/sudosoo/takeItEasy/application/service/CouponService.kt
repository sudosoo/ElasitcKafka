package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.coupon.CreateCouponRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto

interface CouponService {
    fun couponIssuance(requestDto: CouponIssuanceRequestDto)

}
