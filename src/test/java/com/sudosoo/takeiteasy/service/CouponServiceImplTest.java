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
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @DisplayName("priceCouponCreate")
    void priceCouponCreate() throws Exception {
        //given
        CreateEventRequestDto priceRequestDto = new CreateEventRequestDto("TestEvent","2024-12-25 00:00","2024-12-25 00:00",10,35000L);
        LocalDateTime couponDeadline= localDateTimeFormatter(priceRequestDto.getCouponDeadline());
        Coupon coupon = Coupon.priceOf(priceRequestDto,couponDeadline);
        when(couponRepository.save(any())).thenReturn(coupon);

        //when
        Coupon resultCoupon = couponService.priceCouponCreate(priceRequestDto);

        //then
        assertEquals(resultCoupon.getDiscountPrice(),priceRequestDto.getDiscountPrice());

    }

    @Test
    @DisplayName("rateCouponCreate")
    void rateCouponCreate() throws Exception {
        //given
        CreateEventRequestDto rateRequestDto = new CreateEventRequestDto("TestEvent","2024-12-25 00:00","2024-12-25 00:00",10,10);
        LocalDateTime couponDeadline= localDateTimeFormatter(rateRequestDto.getCouponDeadline());
        Coupon coupon = Coupon.rateOf(rateRequestDto,couponDeadline);
        when(couponRepository.save(any())).thenReturn(coupon);

        //when
        Coupon resultCoupon = couponService.rateCouponCreate(rateRequestDto);

        //then
        assertEquals(rateRequestDto.getDiscountRate(),resultCoupon.getDiscountRate());
    }

    private LocalDateTime localDateTimeFormatter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}