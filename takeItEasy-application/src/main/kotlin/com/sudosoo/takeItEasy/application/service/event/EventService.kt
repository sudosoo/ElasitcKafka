package com.sudosoo.takeItEasy.application.service.event

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto

interface EventService {
    fun create(requestDto: CreateEventRequestDto): EventResponseDto

}
