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
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(nullable = false)
    @JoinColumn(name = "member_id")
    private Long memberId;
    private boolean useCheck = false;

    public Coupon(Coupon coupon, Long memberId) {
        this.coupon = coupon;
        this.memberId = memberId;
    }
    public void useCoupon() {
        this.useCheck = true;
    }


}