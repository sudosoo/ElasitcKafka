package com.sudosoo.takeItEasy.application.service.coupon

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.core.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import com.sudosoo.takeItEasy.domain.repository.common.DeadLetterRepository
import com.sudosoo.takeItEasy.domain.repository.coupon.CouponRepository
import com.sudosoo.takeItEasy.domain.repository.coupon.RewardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponService(
    private val couponRepository: CouponRepository,
    private val deadLetterRepository: DeadLetterRepository,
    private val rewardRepository: RewardRepository
    ): JpaService<Coupon, Long>, JpaSpecificService<Coupon, Long> {
    override var jpaRepository: BaseRepository<Coupon, Long> = couponRepository
    override val jpaSpecRepository: BaseRepository<Coupon, Long> = couponRepository

    @Transactional(timeout = 5)
    fun couponIssuance(requestDto: CouponIssuanceRequestDto) {
        if (!deadLetterRepository.existsById(requestDto.eventId)){
            throw IllegalArgumentException("Event is not found") }
        if(!rewardRepository.existsById(requestDto.couponWrapperId)){
            throw IllegalArgumentException("Coupon is not found") }
        val couponWrapper = rewardRepository.findByIdForUpdate(requestDto.couponWrapperId).orElseThrow{IllegalArgumentException("Coupon is not found")}
        val coupon = Coupon.of(couponWrapper,requestDto.memberId)
        couponWrapper.decreaseCouponQuantity()
        save(coupon)
    }
}
