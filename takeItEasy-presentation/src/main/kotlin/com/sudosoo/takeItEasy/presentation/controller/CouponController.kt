package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.service.CouponService
import com.sudosoo.takeItEasy.application.service.CouponWrapperService
import com.sudosoo.takeItEasy.domain.entity.CouponWrapper
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/couponWrapper")
class CouponController(
    private val couponService : CouponService
) {



    @PostMapping("/couponIssuance", name = "couponIssuance")
    fun couponIssuance(@Valid @RequestBody requestDto: CouponIssuanceRequestDto): ResponseEntity<Unit> {
        couponService.couponIssuance(requestDto)

        return ResponseEntity.ok().build()
    }

}