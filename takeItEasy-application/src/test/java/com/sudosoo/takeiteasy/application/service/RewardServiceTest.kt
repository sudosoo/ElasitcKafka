package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.RewardCreateDto
import com.sudosoo.takeItEasy.application.service.coupon.RewardService
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.Reward
import com.sudosoo.takeItEasy.domain.entity.Reward.testRateOf
import com.sudosoo.takeItEasy.domain.repository.coupon.RewardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.util.*

class RewardServiceTest {
    @Mock
    lateinit var rewardRepository: RewardRepository

    @Mock
    lateinit var jpaService: JpaService<Coupon, Long>

    @InjectMocks
    lateinit var rewardService: RewardService

    @Mock
    val mockEvent: Event = mock(Event::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun `가격할인 쿠폰이 만들어 져야 한다`() {
        val requestDto = RewardCreateDto(
            1L,
            LocalDate.now().toString(),
            100,
            10000L,
            null,
            null
        )
        val testPriceCoupon = Reward.testPriceOf(1L, 1L, 100, LocalDate.now().toString(), 10000L)
        `when`(rewardRepository.save(testPriceCoupon)).thenReturn(testPriceCoupon)

        // When
        rewardService.create(requestDto)

        // Then
        verify(rewardRepository, times(1)).save(any(Reward::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun `할인율 쿠폰이 만들어 져야 한다`() {
        val requestDto = RewardCreateDto(
            1L,
            LocalDate.now().toString(),
            100,
            null,
            10,
            null
        )
        val testRateCoupon = testRateOf(
            1L, 1L,10, requestDto.couponDeadline, requestDto.discountRate!!)
        `when`(rewardRepository.save(testRateCoupon)).thenReturn(testRateCoupon)

        // When
        rewardService.create(requestDto)

        // Then
        verify(rewardRepository, times(1)).save(any(Reward::class.java))
    }

    @Test
    fun `요청에 할인율과 할인가격이 둘 다 있으면 생성되지 않는다`() {
        //given
        val requestDto =
            RewardCreateDto(1L, "2024-12-31T23:59:59", 10, 10000L, 10, null)

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            rewardService.create(requestDto)
        }

        //then
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }

    @Test
    fun `요청에 할인율과 할인가격이 둘 다 없으면 생성 되지 않는다`() {
        //given
        val requestDto =
            RewardCreateDto(1L, "2024-12-31T23:59:59", 10, null, null, null)

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            rewardService.create(requestDto)
        }

        //then
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }
}