package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.entity.Event;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.PessimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EventServiceImplTest {
    @Mock
    private MemberService memberService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CouponService couponService;
    @InjectMocks
    private EventServiceImpl eventService;
    private final CreateEventRequestDto requestDto = new CreateEventRequestDto("TestEvent",LocalDateTime.now().toString(),LocalDateTime.now().toString(),10,10);
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Member mockMember = mock(Member.class);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(mockMember);
    }

    @Test
    @DisplayName("EventCreateTestToRateCoupon")
    void createdEventByRateCoupon() {
        Coupon mockRateCoupon = mock(Coupon.class);
        when(couponService.rateCouponCreate(any())).thenReturn(mockRateCoupon);

        CreateEventRequestDto rateRequestDto = new CreateEventRequestDto("TestEvent","2024-12-31T23:59:59","2024-12-31T23:59:59",10,10);
        Event testEvent = Event.of(rateRequestDto);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // When
        eventService.createdEvent(rateRequestDto);

        // Then
        verify(couponService, times(1)).rateCouponCreate(rateRequestDto);
        verify(eventRepository, times(1)).save(any(Event.class));
    }
    @Test
    @DisplayName("EventCreateTestToPriceCoupon")
    void createdEventByPriceCoupon() {
        Coupon mockPriceCoupon = mock(Coupon.class);
        when(couponService.priceCouponCreate(any())).thenReturn(mockPriceCoupon);

        CreateEventRequestDto priceRequestDto = new CreateEventRequestDto("TestEvent","2024-12-31T23:59:59","2024-12-31T23:59:59",10,10000L);
        Event testEvent = Event.of(priceRequestDto);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // When
        eventService.createdEvent(priceRequestDto);

        // Then
        verify(couponService, times(1)).priceCouponCreate(priceRequestDto);
        verify(eventRepository, times(1)).save(any(Event.class));
    }



    @Test
    @DisplayName("쿠폰 발급 테스트 (멀티 스레드)")
    void couponIssuanceForMultiThreadTest() throws InterruptedException {
        CouponIssuanceRequestDto couponIssuanceRequestDto = new CouponIssuanceRequestDto(1L,1L,1L);
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);
        Event event = Event.of(requestDto);
        when(eventRepository.findByEventIdForUpdate(anyLong())).thenReturn(Optional.ofNullable(event));

        for (int i = 0; i < numberOfExecute; i++) {
            final int threadNumber = i + 1;
            service.execute(() -> {
                try {
                    eventService.couponIssuance(couponIssuanceRequestDto);
                    successCount.getAndIncrement();
                    System.out.println("Thread " + threadNumber + " - 성공");
                } catch (PessimisticLockingFailureException e) {
                    System.out.println("Thread " + threadNumber + " - 락 충돌 감지");
                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        // 성공한 경우의 수가 10개라고 가정.
        assertThat(successCount.get()).isEqualTo(10);
    }

}