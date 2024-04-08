package com.sudosoo.takeItEasy.domain.entity;

import com.sudosoo.takeItEasy.domain.support.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter

public class CouponWrapper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    private Long eventId;

    @Column(nullable = false)
    private LocalDate couponDeadline;

    private int discountRate;

    private long discountPrice;

    private int couponQuantity = 0;


    public void decreaseCouponQuantity() {
        if (couponQuantity > 0) {
            couponQuantity--;
        } else {
            throw new IllegalStateException("남은 쿠폰 수량이 없습니다.");
        }
    }


    @Builder
    public CouponWrapper(String couponName, long eventId, LocalDate couponDeadline, int discountRate, long discountPrice, Boolean useCheck, int couponQuantity) {
        this.couponName = couponName;
        this.eventId = eventId;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.couponQuantity = couponQuantity;
    }

    private CouponWrapper(Long id, long eventId, String couponName, int couponQuantity, LocalDate couponDeadline, long discountPrice) {
        this.id = id;
        this.eventId = eventId;
        this.couponName = couponName + discountPrice;
        this.couponQuantity = couponQuantity;
        this.couponDeadline = couponDeadline;
        this.discountPrice = discountPrice;
    }

    public static CouponWrapper testPriceOf(Long id, long eventId, String couponName, int couponQuantity, String couponDeadline, Long discountPrice) {
        return new CouponWrapper(id, eventId, couponName, couponQuantity, LocalDate.parse(couponDeadline), discountPrice);
    }

    private CouponWrapper(Long id, long eventId, String couponName, int couponQuantity, LocalDate couponDeadline, int discountRate) {
        this.id = id;
        this.eventId = eventId;
        this.couponName = couponName + discountRate;
        this.couponQuantity = couponQuantity;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
    }

    public static CouponWrapper testRateOf(Long id, long eventId, String couponName, int couponQuantity, String couponDeadline, Integer discountRate) {
        return new CouponWrapper(id, eventId, couponName, couponQuantity, LocalDate.parse(couponDeadline), discountRate);
    }

    protected CouponWrapper() {
    }


}