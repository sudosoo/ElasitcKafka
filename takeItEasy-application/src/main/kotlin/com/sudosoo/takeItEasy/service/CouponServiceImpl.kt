package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.event.CreateEventRequestDto
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto
import com.sudosoo.takeiteasy.entity.Coupon
import com.sudosoo.takeiteasy.repository.CouponRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@RequiredArgsConstructor
class CouponServiceImpl(val couponRepository: CouponRepository) : CouponService {
    override fun priceCouponCreate(requestDto: CreateEventRequestDto): Coupon {
        val coupon: Coupon = Coupon.priceOf(requestDto)
        return couponRepository.save<Coupon>(coupon)
    }

    override fun rateCouponCreate(requestDto: CreateEventRequestDto): Coupon {
        val coupon: Coupon = Coupon.rateOf(requestDto)
        return couponRepository.save<Coupon>(coupon)
    }
}
