package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponWrapperCreateDto
import com.sudosoo.takeItEasy.application.service.CouponWrapperServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.CouponWrapper
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.CouponWrapperRepository
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.util.*

class CouponWrapperServiceImplTest {
    @Mock
    lateinit var couponWrapperRepository: CouponWrapperRepository

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var jpaService: JpaService<Coupon, Long>

    @InjectMocks
    lateinit var couponWrapperService: CouponWrapperServiceImpl

    @Mock
    val mockEvent: Event = mock(Event::class.java)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun `가격할인 쿠폰이 만들어 져야 한다`() {
        val requestDto = CouponWrapperCreateDto(
            1L,
            LocalDate.now().toString(),
            100,
            10000L,
            null,
            null
        )
        val testPriceCoupon = CouponWrapper.testPriceOf(1L, 1L, "testEvent", 100, LocalDate.now().toString(), 10000L)
        `when`(couponWrapperRepository.save(testPriceCoupon)).thenReturn(testPriceCoupon)
        `when`(eventRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockEvent))

        // When
        couponWrapperService.create(requestDto)

        // Then
        // Assertions
        verify(eventRepository, times(1)).findById(requestDto.eventId);
        verify(couponWrapperRepository, times(1)).save(any(CouponWrapper::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun `할인율 쿠폰이 만들어 져야 한다`() {
        val requestDto = CouponWrapperCreateDto(
            1L,
            LocalDate.now().toString(),
            100,
            null,
            10,
            null
        )
        val testCoupon = CouponWrapper.testRateOf(
            1L,
            1L,
            requestDto.eventName,
            10,
            requestDto.couponDeadline,
            requestDto.discountRate!!
        )
        `when`(couponWrapperRepository.save(testCoupon)).thenReturn(testCoupon)
        `when`(eventRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockEvent))

        // When
        couponWrapperService.create(requestDto)

        // Then
        verify(eventRepository, times(1)).findById(requestDto.eventId);
        verify(couponWrapperRepository, times(1)).save(any(CouponWrapper::class.java))
    }

    @Test
    fun `요청에 할인율과 할인가격이 둘 다 있으면 생성되지 않는다`() {
        //given
        val requestDto =
            CouponWrapperCreateDto(1L, "2024-12-31T23:59:59", 10, 10000L, 10, null)

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            couponWrapperService.create(requestDto)
        }

        //then
        verify(eventRepository, never())
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }

    @Test
    fun `요청에 할인율과 할인가격이 둘 다 없으면 생성되지 않는다`() {
        //given
        val requestDto =
            CouponWrapperCreateDto(1L, "2024-12-31T23:59:59", 10, null, null, null)

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            couponWrapperService.create(requestDto)
        }

        //then
        verify(eventRepository, never())
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }
}