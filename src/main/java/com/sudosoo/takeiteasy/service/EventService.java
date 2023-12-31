package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;

public interface EventService {

    EventResponseDto createdEvent(CreateEventRequestDto requestDto);

    void couponIssuance(CouponIssuanceRequestDto requestDto);

}
