package com.sudosoo.takeItEasy.presentation.controller.coupon

import com.sudosoo.takeItEasy.application.dto.coupon.RewardCreateDto
import com.sudosoo.takeItEasy.application.service.coupon.RewardService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/couponWrapper")
class CouponWrapperController(
    private val rewardService : RewardService
) {
    @PostMapping("/create", name = "couponWrapperCreate")
    fun createEvent(@RequestBody @Valid requestDto: RewardCreateDto) {
        rewardService.create(requestDto)
    }


}