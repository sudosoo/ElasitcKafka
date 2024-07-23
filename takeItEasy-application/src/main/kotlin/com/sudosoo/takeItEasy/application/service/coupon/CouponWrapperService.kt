package com.sudosoo.takeItEasy.application.service.coupon

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponWrapperCreateDto
import com.sudosoo.takeItEasy.domain.entity.CouponWrapper
import com.sudosoo.takeItEasy.domain.repository.CouponWrapperRepository
import com.sudosoo.takeItEasy.domain.repository.DeadLetterRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponWrapperService(
    private val couponWrapperRepository: CouponWrapperRepository,
    private val deadLetterRepository: DeadLetterRepository

): JpaService<CouponWrapper, Long>, JpaSpecificService<CouponWrapper, Long> {

    override var jpaRepository: BaseRepository<CouponWrapper, Long> = couponWrapperRepository
    override val jpaSpecRepository: BaseRepository<CouponWrapper, Long> = couponWrapperRepository

    fun create(requestDto: CouponWrapperCreateDto){
        validateDiscountFields(requestDto)
        val event = deadLetterRepository.findById(requestDto.eventId).orElseThrow{IllegalArgumentException("Event is not found")}
        requestDto.eventName = event.operation.name
        val coupon = if (requestDto.discountRate != null) {
            //할인율 적용 쿠폰일때
            rateCouponCreate(requestDto)
        } else {
            //할인가격 적용 쿠폰 일때
            priceCouponCreate(requestDto)
        }
        save(coupon)
    }

    private fun validateDiscountFields(requestDto: CouponWrapperCreateDto) {
        require(!(requestDto.discountRate == null && requestDto.discountPrice == null) &&
                !(requestDto.discountRate != null && requestDto.discountPrice != null))
        { "discountRate 또는 discountPrice 중 하나만 존재해야 합니다." }
    }

    private fun priceCouponCreate(requestDto: CouponWrapperCreateDto): CouponWrapper {
        return CouponWrapper.priceOf(
            requestDto.eventId,requestDto.eventName ,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountPrice)
    }

    private fun rateCouponCreate(requestDto: CouponWrapperCreateDto): CouponWrapper {
        return CouponWrapper.rateOf(
            requestDto.eventId,requestDto.eventName  ,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountRate)
    }

}
