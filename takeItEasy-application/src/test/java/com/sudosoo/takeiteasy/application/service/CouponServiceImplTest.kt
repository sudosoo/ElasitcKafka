package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.service.CouponServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.repository.CouponRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
class CouponServiceImplTest{
    @Mock
    lateinit var couponRepository: CouponRepository
    @Mock
    lateinit var jpaService: JpaService<Coupon,Long>
    @InjectMocks
    lateinit var couponService: CouponServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun `가격할인 쿠폰이 만들어 져야 한다`() {
        val requestDto = CreateEventRequestDto(
            "TestEvent",
            LocalDateTime.now().toString(),
            LocalDateTime.now().toString(),
            10,
            10000L,
            null
        )
        val testCoupon = Coupon.priceOf(requestDto.eventName, requestDto.couponDeadline, requestDto.discountPrice!!)
        `when`(couponRepository.save(testCoupon)).thenReturn(testCoupon)

        // When
        val createdCoupon = couponService.priceCouponCreate(requestDto)

        // Then
        assertThat(createdCoupon.equals(testCoupon))
        verify(couponRepository, times(1)).save(createdCoupon)
    }

    @Test
    @Throws(Exception::class)
    fun `할인율 쿠폰이 만들어 져야 한다`() {
        val requestDto = CreateEventRequestDto(
            "TestEvent",
            LocalDateTime.now().toString(),
            LocalDateTime.now().toString(),
            10,
            null,
            10
        )
        val testCoupon = Coupon.rateOf(requestDto.eventName, requestDto.couponDeadline, requestDto.discountRate!!)
        `when`(couponRepository.save(testCoupon)).thenReturn(testCoupon)

        // When
        val createdCoupon = couponService.rateCouponCreate(requestDto)

        // Then
        assertThat(createdCoupon.equals(testCoupon))
        verify(couponRepository, times(1)).save(createdCoupon)
    }
}