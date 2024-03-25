package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.service.CouponService
import com.sudosoo.takeItEasy.application.service.EventServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.*
import org.springframework.dao.PessimisticLockingFailureException
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

internal class EventServiceImplTest{
    @Mock
    lateinit var eventRepository: EventRepository
    @Mock
    lateinit var couponService: CouponService
    @Mock
    lateinit var jpaService: JpaService<Event, Long>
    @InjectMocks
    lateinit var eventService: EventServiceImpl

    var testEvent = Event(1L, "TestEvent", 1L ,null,10,LocalDateTime.now().plusDays(1) )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `할인율 쿠폰으로 이벤트 만들기`() {
        val requestDto = CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, null, 10)
        val testRateCoupon = Coupon.testRateOf(1L,requestDto.eventName, requestDto.eventDeadline,requestDto.discountRate!!)
        `when`(couponService.rateCouponCreate(requestDto)).thenReturn(testRateCoupon)
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)

        // When
        eventService.create(requestDto)

        // Then
        verify(couponService,times(1)).rateCouponCreate(requestDto)
        verify(eventRepository,times(1)).save(any(Event::class.java))
    }

    @Test
    fun `가격할인 쿠폰으로 이벤트 만들기`() {
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, 10000L, null)
        val testPriceCoupon = Coupon.testPriceOf(1L,requestDto.eventName, requestDto.eventDeadline,requestDto.discountPrice!!)
        `when`(couponService.priceCouponCreate(requestDto)).thenReturn(testPriceCoupon)
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)

        // When
        eventService.create(requestDto)

        // Then
        verify(couponService, times(1)).priceCouponCreate(requestDto)
        verify(eventRepository, times(1)).save(any(Event::class.java))
    }


    @Test
    @Throws(InterruptedException::class)
    fun `멀티 스레드 환경 선착순 쿠폰 발급 테스트`() {
        val couponIssuanceRequestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        val testEventDeadLine = LocalDateTime.now().toString()
        val successCount = AtomicInteger()
        val numberOfExecute = 100000
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, 10000L, null)
        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfExecute)
        val mockCoupon = mock(Coupon::class.java)
        val event = Event.of(requestDto.eventName, requestDto.couponQuantity, testEventDeadLine, mockCoupon)
        `when`(eventRepository.findByEventIdForUpdate(anyLong())).thenReturn(Optional.ofNullable(event))

        for (i in 0 until numberOfExecute) {
            val threadNumber = i + 1
            service.execute {
                try {
                    eventService.couponIssuance(couponIssuanceRequestDto)
                    successCount.getAndIncrement()
                    println("Thread $threadNumber - 성공")
                } catch (e: PessimisticLockingFailureException) {
                    println("Thread $threadNumber - 락 충돌 감지")
                } catch (e: Exception) {
                    println("Thread " + threadNumber + " - " + e.message)
                }
                latch.countDown()
            }
        }
        latch.await()

        // 성공한 경우의 수가 10개라고 가정.
        AssertionsForClassTypes.assertThat(successCount.get()).isEqualTo(10)
    }
}