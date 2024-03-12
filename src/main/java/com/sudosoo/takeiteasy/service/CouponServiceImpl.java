package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService , JpaService<Coupon,Long> {
    private final CouponRepository couponRepository;
    @Override
    public JpaRepository<Coupon, Long> getJpaRepository() {
        return couponRepository;
    }

    @Override
    public Coupon createCoupon(CreateEventRequestDto requestDto) {
        Coupon coupon;
        if(requestDto.getDiscountRate() == 0){
            coupon = Coupon.priceOf(requestDto);

        }else{
            coupon = Coupon.rateOf(requestDto);

        }
        return save(coupon);
    }

}
