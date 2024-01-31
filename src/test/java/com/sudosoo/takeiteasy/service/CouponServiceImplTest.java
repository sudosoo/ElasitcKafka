package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import com.sudosoo.takeiteasy.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CouponServiceImplTest {

    CouponRepository couponRepository = mock(CouponRepository.class);

    CouponService couponService = new CouponServiceImpl(couponRepository);

    @BeforeEach
    void setUp() {

    }
    @Test
    @DisplayName("priceCouponCreate")
    void priceCouponCreate() throws Exception {
        //given
        CreateEventRequestDto createEventRequestDto = new CreateEventRequestDto("TestEvent","2024-12-25 00:00","2024-12-25 00:00",10,35000L);

        //when

        //then
    }

    @Test
    @DisplayName("priceCouponCreate")
    void rateCouponCreate() throws Exception {
        //given
        CreateEventRequestDto createEventRequestDto = new CreateEventRequestDto("TestEvent","2024-12-25 00:00","2024-12-25 00:00",10,10);

        //when

        //then
    }
}