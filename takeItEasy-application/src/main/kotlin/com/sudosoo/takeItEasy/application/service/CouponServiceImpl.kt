package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.repository.CouponRepository
import com.sudosoo.takeItEasy.domain.repository.CouponWrapperRepository
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponServiceImpl(
    private val couponRepository: CouponRepository,
    private val eventRepository: EventRepository,
    private val couponWrapperRepository: CouponWrapperRepository
    ) : CouponService, JpaService<Coupon, Long>,JpaSpecificService<Coupon, Long> {
    override var jpaRepository: BaseRepository<Coupon, Long> = couponRepository
    override val jpaSpecRepository: BaseRepository<Coupon, Long> = couponRepository

    @Transactional(timeout = 5)
    override fun couponIssuance(requestDto: CouponIssuanceRequestDto) {
        if (!eventRepository.existsById(requestDto.eventId)){
            throw IllegalArgumentException("Event is not found") }
        if(!couponWrapperRepository.existsById(requestDto.couponWrapperId)){
            throw IllegalArgumentException("Coupon is not found") }
        val couponWrapper = couponWrapperRepository.findByIdForUpdate(requestDto.couponWrapperId).orElseThrow{IllegalArgumentException("Coupon is not found")}
        val coupon = Coupon.of(couponWrapper,requestDto.memberId)
        couponWrapper.decreaseCouponQuantity()
        save(coupon)
    }
}
