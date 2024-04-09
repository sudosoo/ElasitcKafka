package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    @JoinColumn(name = "coupon_wrapper_id")
    private CouponWrapper couponWrapperId;
    @Column(nullable = false)
    @JoinColumn(name = "member_id")
    private Long memberId;
    private boolean useCheck = false;

    private Coupon(CouponWrapper couponWrapper, Long memberId) {
        this.couponWrapperId = couponWrapper;
        this.memberId = memberId;
    }
    public static Coupon of(CouponWrapper couponWrapper, Long memberId) {
        return new Coupon(couponWrapper, memberId);
    }
    public void useCoupon() {
        this.useCheck = true;
    }


}