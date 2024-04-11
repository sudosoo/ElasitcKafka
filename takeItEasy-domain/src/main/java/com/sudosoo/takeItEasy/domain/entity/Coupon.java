package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_wrapper_id")
    private CouponWrapper couponWrapperId;
    @Column(nullable = false)
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

    public Coupon(){

    }
    public Long getId() {
        return id;
    }

    public CouponWrapper getCouponWrapperId() {
        return couponWrapperId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public boolean isUseCheck() {
        return useCheck;
    }
}