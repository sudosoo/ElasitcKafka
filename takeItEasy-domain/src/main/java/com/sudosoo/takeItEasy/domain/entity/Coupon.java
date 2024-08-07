package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_wrapper_id")
    private Reward rewardId;
    @Column(nullable = false)
    private Long memberId;
    private boolean useCheck = false;

    private Coupon(Reward reward, Long memberId) {
        this.rewardId = reward;
        this.memberId = memberId;
    }
    public static Coupon of(Reward reward, Long memberId) {
        return new Coupon(reward, memberId);
    }
    public void useCoupon() {
        this.useCheck = true;
    }

    public Coupon(){

    }
    public Long getId() {
        return id;
    }

    public Reward getCouponWrapperId() {
        return rewardId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public boolean isUseCheck() {
        return useCheck;
    }
}