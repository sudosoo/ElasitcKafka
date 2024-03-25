package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.service.CouponService
import com.sudosoo.takeItEasy.application.service.EventServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.Heart
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.`when`
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


    private val requestDto =
        CreateEventRequestDto("TestEvent", LocalDateTime.now().toString(), LocalDateTime.now().toString(), 10, null, 10)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `할인율 쿠폰으로 이벤트 만들기`() {
        val requestDto = CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, null, 10)
        val mockRateCoupon = Mockito.mock(Coupon::class.java)
        Mockito.`when`(couponService!!.priceCouponCreate(requestDto)).thenReturn(mockRateCoupon)

        val mockEvent = Mockito.mock(
            Event::class.java
        )
        Mockito.`when`(
            eventRepository!!.save(
                ArgumentMatchers.any(
                    Event::class.java
                )
            )
        ).thenReturn(mockEvent)

        // When
        eventService.create(requestDto)

        // Then
        Mockito.verify(couponService, Mockito.times(1)).priceCouponCreate(ArgumentMatchers.any())
        Mockito.verify(eventRepository, Mockito.times(1)).save(
            ArgumentMatchers.any(
                Event::class.java
            )
        )
    }

    @Test
    fun `가격할인 쿠폰으로 이벤트 만들기`() {
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, 10000L, null)
        val mockPriceCoupon = Mockito.mock(Coupon::class.java)
        `when`(couponService.rateCouponCreate(requestDto)).thenReturn(mockPriceCoupon)

        val mockEvent = Mockito.mock(
            Event::class.java
        )
        `when`(
            eventRepository.save(
                ArgumentMatchers.any(
                    Event::class.java
                )
            )
        ).thenReturn(mockEvent)

        // When
        eventService.create(requestDto)

        // Then
        Mockito.verify(couponService, Mockito.times(1)).rateCouponCreate(requestDto)
        Mockito.verify(eventRepository, Mockito.times(1)).save(
            ArgumentMatchers.any(
                Event::class.java
            )
        )
    }


    @Test
    @Throws(InterruptedException::class)
    fun `멀티 스레드 환경 선착순 쿠폰 발급 테스트`() {
        val couponIssuanceRequestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        val testEventDeadLine = LocalDateTime.now().toString()
        val successCount = AtomicInteger()
        val numberOfExecute = 100000
        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfExecute)
        val mockCoupon = Mockito.mock(Coupon::class.java)
        val event = Event.of(requestDto.eventName, requestDto.couponQuantity, testEventDeadLine, mockCoupon)
        `when`(eventRepository.findByEventIdForUpdate(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.ofNullable(event))

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