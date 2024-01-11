package com.sudosoo.takeiteasy.dto.coupon;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CouponIssuanceRequestDto {

    private Long memberId;
    private Long eventId;
    private Long couponId;


}
