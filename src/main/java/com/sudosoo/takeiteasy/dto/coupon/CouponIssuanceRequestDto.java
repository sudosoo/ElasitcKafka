package com.sudosoo.takeiteasy.dto.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssuanceRequestDto {

    private Long memberId;
    private Long eventId;
    private Long couponId;

}
