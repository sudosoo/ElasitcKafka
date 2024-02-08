package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon priceCouponCreate(CreateEventRequestDto requestDto) {
        LocalDateTime couponDeadline = localDateTimeFormatter(requestDto.getCouponDeadline());
        Coupon coupon = Coupon.priceOf(requestDto,couponDeadline);
        return couponRepository.save(coupon);
    }

    private LocalDateTime localDateTimeFormatter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    @Override
    public Coupon rateCouponCreate(CreateEventRequestDto requestDto) {
        LocalDateTime couponDeadline = localDateTimeFormatter(requestDto.getCouponDeadline());
        Coupon coupon = Coupon.rateOf(requestDto,couponDeadline);

        return couponRepository.save(coupon);
    }
}
