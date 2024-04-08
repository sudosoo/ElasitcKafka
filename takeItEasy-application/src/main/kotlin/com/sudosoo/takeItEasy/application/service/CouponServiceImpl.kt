package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.coupon.CreateCouponRequestDto
import com.sudosoo.takeItEasy.domain.entity.CouponWrapper
import com.sudosoo.takeItEasy.domain.repository.CouponWrapperRepository
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponServiceImpl(
    val couponWrapperRepository: CouponWrapperRepository,
    val eventRepository: EventRepository,

    ) : CouponService, JpaService<CouponWrapper, Long>
{
    override var jpaRepository: JpaRepository<CouponWrapper, Long> = couponWrapperRepository

    fun create(requestDto: CreateCouponRequestDto){
        validateDiscountFields(requestDto)

        val coupon = if (requestDto.discountRate != null) {
            //할인율 적용 쿠폰일때
            rateCouponCreate(requestDto)
        } else {
            //할인가격 적용 쿠폰 일때
            priceCouponCreate(requestDto)
        }
        save(coupon)
    }

    private fun validateDiscountFields(requestDto: CreateCouponRequestDto) {
        require(!(requestDto.discountRate == null && requestDto.discountPrice == null) &&
                !(requestDto.discountRate != null && requestDto.discountPrice != null))
        { "discountRate 또는 discountPrice 중 하나만 존재해야 합니다." }
    }

    private fun priceCouponCreate(requestDto: CreateCouponRequestDto): Coupon {
        return Coupon.priceOf(
            requestDto.eventId ,requestDto.couponName,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountPrice)
    }

    private fun rateCouponCreate(requestDto: CreateCouponRequestDto): Coupon {
        return Coupon.rateOf(
            requestDto.eventId ,requestDto.couponName ,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountRate)
    }


    @Transactional(timeout = 5)
    override fun couponIssuance(requestDto: CouponIssuanceRequestDto) {
        if (!eventRepository.existsById(requestDto.eventId)){
            throw IllegalArgumentException("Event is not found") }
        val coupon = findById(requestDto.couponId)
        coupon.decreaseCouponQuantity()

        save(coupon)
    }
}
