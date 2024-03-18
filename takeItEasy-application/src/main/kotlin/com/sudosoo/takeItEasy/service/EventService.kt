package com.sudosoo.takeItEasy.service

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto

interface EventService {
    fun createdEvent(requestDto: CreateEventRequestDto): EventResponseDto

    fun couponIssuance(requestDto: CouponIssuanceRequestDto)
}
