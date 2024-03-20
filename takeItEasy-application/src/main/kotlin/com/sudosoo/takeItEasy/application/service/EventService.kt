package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto

interface EventService {
    fun createdEvent(requestDto: CreateEventRequestDto): EventResponseDto

    fun couponIssuance(requestDto: CouponIssuanceRequestDto)
}
