package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.repository.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponServiceImpl(val couponRepository: CouponRepository) : CouponService {
    override fun priceCouponCreate(requestDto: CreateEventRequestDto): Coupon {
        val coupon: Coupon = Coupon.priceOf(
            requestDto.eventName,requestDto.couponDeadline,requestDto.discountPrice!!)

        return couponRepository.save<Coupon>(coupon)
    }

    override fun rateCouponCreate(requestDto: CreateEventRequestDto): Coupon {
        val coupon: Coupon = Coupon.rateOf(
            requestDto.eventName,requestDto.couponDeadline,requestDto.discountRate!!)
        return couponRepository.save<Coupon>(coupon)
    }
}
