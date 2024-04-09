package com.sudosoo.takeItEasy.application.dto.coupon

import com.sudosoo.takeItEasy.domain.entity.CouponWrapper

class CreateCouponRequestDto(
    val memberId: Long ,
    val couponQuantity: Int  ,
    val eventId: Long ,
    val couponName :String ,
    val couponDeadline: String ,
    val discountRate: Int? ,
    val discountPrice: Long?)
