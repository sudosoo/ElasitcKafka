package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface EventService {

    EventResponseDto createdEvent(CreateEventRequestDto requestDto);

    void couponIssuance(CouponIssuanceRequestDto requestDto);

}
