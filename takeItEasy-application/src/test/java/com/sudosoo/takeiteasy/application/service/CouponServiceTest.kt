package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.service.coupon.CouponService
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.CouponWrapper
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.CouponRepository
import com.sudosoo.takeItEasy.domain.repository.CouponWrapperRepository
import com.sudosoo.takeItEasy.domain.repository.DeadLetterRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.springframework.dao.PessimisticLockingFailureException
import java.time.LocalDate
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.Executors

class CouponServiceTest(
) {
    @Mock
    lateinit var couponWrapperRepository: CouponWrapperRepository

    @Mock
    lateinit var deadLetterRepository: DeadLetterRepository

    @Mock
    lateinit var couponRepository: CouponRepository
    @Mock
    lateinit var jpaService: JpaService<Coupon, Long>

    @InjectMocks
    lateinit var couponService: CouponService

    @Mock
    val mockEvent: Event = Mockito.mock(Event::class.java)


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun `이벤트가 존재하지 않으면 쿠폰을 사용할 수 없다`() {
        //given
        val requestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        `when`(deadLetterRepository.findById(anyLong())).thenReturn(Optional.empty())

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            couponService.couponIssuance(requestDto)
        }

        //then
        Mockito.verify(deadLetterRepository, never())
        assert(exception.message == "Event is not found")
    }


    @Test
    @Throws(InterruptedException::class)
    fun `멀티 스레드 환경 선착순 쿠폰 발급 테스트`() {
        val maxCouponCount = 10
        val couponWrapper = CouponWrapper.testRateOf(1L, 1L, "testEvent", maxCouponCount, LocalDate.now().toString(), 10)
        val requestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        val successCount = AtomicInteger()
        val numberOfExecute = 10000
        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfExecute)

        `when`(deadLetterRepository.existsById(anyLong())).thenReturn(true)
        `when`(couponWrapperRepository.existsById(anyLong())).thenReturn(true)
        `when`(couponWrapperRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(couponWrapper))


        for (i in 0 until numberOfExecute) {
            val threadNumber = i + 1
            service.execute {
                try {
                    couponService.couponIssuance(requestDto)
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
        assertThat(successCount.get()).isEqualTo(maxCouponCount)
    }

}
