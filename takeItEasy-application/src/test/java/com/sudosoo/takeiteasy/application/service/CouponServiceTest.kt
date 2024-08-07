package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.application.service.coupon.CouponService
import com.sudosoo.takeItEasy.domain.entity.Coupon
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.Reward
import com.sudosoo.takeItEasy.domain.repository.common.DeadLetterRepository
import com.sudosoo.takeItEasy.domain.repository.coupon.CouponRepository
import com.sudosoo.takeItEasy.domain.repository.coupon.RewardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.springframework.dao.PessimisticLockingFailureException
import java.time.LocalDate
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class CouponServiceTest(
) {
    @Mock
    lateinit var rewardRepository: RewardRepository
    @Mock
    lateinit var deadLetterRepository: DeadLetterRepository
    @Mock
    lateinit var couponRepository: CouponRepository
    @Mock
    lateinit var jpaService: JpaService<Coupon, Long>

    @InjectMocks
    lateinit var couponService: CouponService


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    @Throws(InterruptedException::class)
    fun `멀티 스레드 환경 선착순 쿠폰 발급 테스트`() {
        val maxCouponCount = 10
        val reward = Reward.testRateOf(1L, 1L, maxCouponCount, LocalDate.now().toString(), 10)
        val requestDto = CouponIssuanceRequestDto(1L, 1L, 1L)
        val successCount = AtomicInteger()
        val numberOfExecute = 10000
        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfExecute)

        `when`(deadLetterRepository.existsById(anyLong())).thenReturn(true)
        `when`(rewardRepository.existsById(anyLong())).thenReturn(true)
        `when`(rewardRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(reward))


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
