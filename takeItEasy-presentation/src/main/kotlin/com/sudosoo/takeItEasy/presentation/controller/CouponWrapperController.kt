package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.coupon.CouponWrapperCreateDto
import com.sudosoo.takeItEasy.application.dto.coupon.CreateCouponRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.application.service.CouponWrapperService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/couponWrapper")
class CouponWrapperController(
    private val couponWrapperService : CouponWrapperService
) {
    @PostMapping("/create", name = "couponWrapperCreate")
    fun createEvent(@RequestBody @Valid requestDto: CouponWrapperCreateDto): ResponseEntity<EventResponseDto> {
        couponWrapperService.create(requestDto)
        return ResponseEntity.ok().build()
    }


}