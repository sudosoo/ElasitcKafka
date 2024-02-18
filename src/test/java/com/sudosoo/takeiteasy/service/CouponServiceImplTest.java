package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CouponServiceImplTest {

    @Mock
    CouponRepository couponRepository;

    @InjectMocks
    CouponServiceImpl couponService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("priceCouponCreate")
    void priceCouponCreate() throws Exception {
        CreateEventRequestDto priceCouponRequestDto = new CreateEventRequestDto("TestEvent", LocalDateTime.now().toString(),LocalDateTime.now().toString(),10,10000L);
        Coupon testCoupon = Coupon.priceOf(priceCouponRequestDto);
        when(couponRepository.save(testCoupon)).thenReturn(testCoupon);

        // When
        Coupon createdCoupon = couponService.priceCouponCreate(priceCouponRequestDto);

        // Then
        assertEquals(testCoupon, createdCoupon);
        verify(couponRepository,times(1)).save(createdCoupon);
    }

    @Test
    @DisplayName("rateCouponCreate")
    void rateCouponCreate() throws Exception {
        //given
        CreateEventRequestDto rateCouponRequestDto = new CreateEventRequestDto("TestEvent", LocalDateTime.now().toString(),LocalDateTime.now().toString(),10,10);
        Coupon testCoupon = Coupon.rateOf(rateCouponRequestDto);
        when(couponRepository.save(testCoupon)).thenReturn(testCoupon);

        //when
        Coupon createdCoupon = couponService.rateCouponCreate(rateCouponRequestDto);

        //then
        assertEquals(testCoupon, createdCoupon);
        verify(couponRepository,times(1)).save(testCoupon);
    }


}