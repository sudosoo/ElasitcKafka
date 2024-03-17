package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    @Override
    public Coupon priceCouponCreate(CreateEventRequestDto requestDto) {
        Coupon coupon = Coupon.priceOf(requestDto);
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon rateCouponCreate(CreateEventRequestDto requestDto) {
        Coupon coupon = Coupon.rateOf(requestDto);
        return couponRepository.save(coupon);
    }
}
