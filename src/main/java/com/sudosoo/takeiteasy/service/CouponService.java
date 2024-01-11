package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;

public interface CouponService {

    Coupon priceCouponCreate(CreateEventRequestDto requestDto);

    Coupon rateCouponCreate(CreateEventRequestDto requestDto);

}