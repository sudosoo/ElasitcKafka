package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.entity.Event;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EventServiceImplTest {
    @Mock
    EventRepository eventRepository;
    @Mock
    CouponService couponService;
    @InjectMocks EventServiceImpl eventService;
    private final CreateEventRequestDto requestDto = new CreateEventRequestDto("TestEvent",LocalDateTime.now().toString(),LocalDateTime.now().toString(),10,10);
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("EventCreateTest")
    void createdEventByRateCoupon() {
        CreateEventRequestDto requestDto = new CreateEventRequestDto("TestEvent","2024-12-31T23:59:59","2024-12-31T23:59:59",10,10);
        Coupon mockRateCoupon = mock(Coupon.class);
        when(couponService.rateCouponCreate(requestDto)).thenReturn(mockRateCoupon);

        Event mockEvent = mock(Event.class);
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // When
        eventService.createdEvent(requestDto);

        // Then
        verify(couponService, times(1)).rateCouponCreate(requestDto);
        verify(eventRepository, times(1)).save(any(Event.class));
    }
    @Test
    @DisplayName("EventCreateTest")
    void createdEventByPriceCoupon() {
        CreateEventRequestDto requestDto = new CreateEventRequestDto("TestEvent","2024-12-31T23:59:59","2024-12-31T23:59:59",10,10000L);
        Coupon mockPriceCoupon = mock(Coupon.class);
        when(couponService.priceCouponCreate(requestDto)).thenReturn(mockPriceCoupon);

        Event mockEvent = mock(Event.class);
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // When
        eventService.createdEvent(requestDto);

        // Then
        verify(couponService, times(1)).priceCouponCreate(requestDto);
        verify(eventRepository, times(1)).save(any(Event.class));
    }



    @Test
    @DisplayName("쿠폰 발급 테스트 (멀티 스레드)")
    void couponIssuanceForMultiThreadTest() throws InterruptedException {
        CouponIssuanceRequestDto couponIssuanceRequestDto = new CouponIssuanceRequestDto(1L,1L,1L);
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 100000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);
        Coupon mockCoupon = mock(Coupon.class);
        Event event = Event.of(requestDto, LocalDateTime.now(), mockCoupon);
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