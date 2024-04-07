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
import org.junit.jupiter.api.Assertions
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
    fun `할인율 쿠폰으로 이벤트가 만들어진다`() {
        //given
        val requestDto = CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, null, 10)
        val testRateCoupon = Coupon.testRateOf(1L,requestDto.eventName, requestDto.eventDeadline,requestDto.discountRate!!)
        `when`(couponService.rateCouponCreate(requestDto)).thenReturn(testRateCoupon)
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)

        //when
        eventService.create(requestDto)

        //then
        verify(couponService,times(1)).rateCouponCreate(requestDto)
        verify(eventRepository,times(1)).save(any(Event::class.java))
    }

    @Test
    fun `가격할인 쿠폰으로 이벤트가 만들어진다`() {
        //given
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, 10000L, null)
        val testPriceCoupon = Coupon.testPriceOf(1L,requestDto.eventName, requestDto.eventDeadline,requestDto.discountPrice!!)
        `when`(couponService.priceCouponCreate(requestDto)).thenReturn(testPriceCoupon)
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)

        //when
        eventService.create(requestDto)

        //then
        verify(couponService, times(1)).priceCouponCreate(requestDto)
        verify(eventRepository, times(1)).save(any(Event::class.java))
    }

    @Test
    fun `요청에 할인율과 할인가격이 둘 다 있으면 생성되지 않는다`() {
        //given
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, 10000L, 10)
        val testPriceCoupon = mock(Coupon::class.java)
        `when`(couponService.priceCouponCreate(requestDto)).thenReturn(testPriceCoupon)

        //when
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            eventService.create(requestDto)
        }

        //then
        verify(eventRepository, never())
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }
    @Test
    fun `요청에 할인율과 할인가격이 둘 다 없으면 생성되지 않는다`() {
        //given
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59", "2024-12-31T23:59:59", 10, null, null)
        val testPriceCoupon = mock(Coupon::class.java)
        `when`(couponService.priceCouponCreate(requestDto)).thenReturn(testPriceCoupon)

        //when
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            eventService.create(requestDto)
        }

        //then
        verify(eventRepository, never())
        assert(exception.message == "discountRate 또는 discountPrice 중 하나만 존재해야 합니다.")
    }

    @Test
    fun `이벤트가 존재하지 않으면 쿠폰을 사용할 수 없다`() {
        //given
        val requestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        `when`(eventRepository.findByEventIdForUpdate(anyLong())).thenReturn(Optional.empty())

        //when
        val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            eventService.couponIssuance(requestDto)
        }

        //then
        verify(eventRepository, never())
        assert(exception.message == "Event is not found")
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