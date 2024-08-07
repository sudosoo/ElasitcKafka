package com.sudosoo.takeItEasy.application.service.coupon

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.core.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.coupon.RewardCreateDto
import com.sudosoo.takeItEasy.domain.entity.Reward
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import com.sudosoo.takeItEasy.domain.repository.coupon.RewardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RewardService(
    private val rewardRepository: RewardRepository
): JpaService<Reward, Long>, JpaSpecificService<Reward, Long> {

    override var jpaRepository: BaseRepository<Reward, Long> = rewardRepository
    override val jpaSpecRepository: BaseRepository<Reward, Long> = rewardRepository

    fun create(requestDto: RewardCreateDto){
        validateDiscountFields(requestDto)
        val coupon = if (requestDto.discountRate != null) {
            rateCouponCreate(requestDto)
        } else {
            priceCouponCreate(requestDto)
        }
        save(coupon)
    }

    private fun validateDiscountFields(requestDto: RewardCreateDto) {
        require(!(requestDto.discountRate == null && requestDto.discountPrice == null) &&
                !(requestDto.discountRate != null && requestDto.discountPrice != null))
        { "discountRate 또는 discountPrice 중 하나만 존재해야 합니다." }
    }

    private fun priceCouponCreate(requestDto: RewardCreateDto): Reward {
        return Reward.priceOf(
            requestDto.eventId,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountPrice)
    }

    private fun rateCouponCreate(requestDto: RewardCreateDto): Reward {
        return Reward.rateOf(
            requestDto.eventId,requestDto.couponQuantity ,requestDto.couponDeadline ,requestDto.discountRate)
    }

}
