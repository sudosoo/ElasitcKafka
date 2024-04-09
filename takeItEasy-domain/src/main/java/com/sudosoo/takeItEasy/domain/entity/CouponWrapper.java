package com.sudosoo.takeItEasy.domain.entity;

import com.sudosoo.takeItEasy.domain.support.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    private CouponWrapper(Long id, long eventId, String eventName, int couponQuantity, LocalDate couponDeadline, Long discountPrice) {
        this.id = id;
        this.eventId = eventId;
        this.couponName = eventName + discountPrice;
        this.couponQuantity = couponQuantity;
        this.couponDeadline = couponDeadline;
        this.discountPrice = discountPrice;
    }

    public static CouponWrapper testPriceOf(Long id, long eventId, String eventName, int couponQuantity, String couponDeadline, Long discountPrice) {
        return new CouponWrapper(id, eventId, eventName, couponQuantity, LocalDate.parse(couponDeadline), discountPrice);
    }

    public CouponWrapper( long eventId ,String eventName, int couponQuantity, LocalDate couponDeadline,  long discountPrice) {
        this.couponName = eventName + discountPrice;
        this.eventId = eventId;
        this.couponDeadline = couponDeadline;
        this.discountPrice = discountPrice;
        this.couponQuantity = couponQuantity;
    }

    public static CouponWrapper priceOf(long eventId, String couponName, int couponQuantity, String couponDeadline, Long discountPrice) {
        return new CouponWrapper(eventId, couponName, couponQuantity, LocalDate.parse(couponDeadline), discountPrice);
    }



    private CouponWrapper(Long id ,long eventId, String eventName, int couponQuantity, LocalDate couponDeadline, int discountRate) {
        this.id = id;
        this.eventId = eventId;
        this.couponName = eventName + discountRate;
        this.couponQuantity = couponQuantity;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
    }
    public static CouponWrapper testRateOf(Long id, long eventId, String eventName, int couponQuantity, String couponDeadline, int discountRate) {
        return new CouponWrapper(id, eventId, eventName, couponQuantity, LocalDate.parse(couponDeadline), discountRate);
    }

    private CouponWrapper(long eventId, String eventName, int couponQuantity, LocalDate couponDeadline, int discountRate) {
        this.eventId = eventId;
        this.couponName = eventName + discountRate;
        this.couponQuantity = couponQuantity;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
    }

    public static CouponWrapper rateOf(long eventId, String couponName, int couponQuantity, String couponDeadline, Integer discountRate) {
        return new CouponWrapper(eventId, couponName, couponQuantity, LocalDate.parse(couponDeadline), discountRate);
    }

    protected CouponWrapper() {
    }


    public Long getId() {
        return this.id;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public Long getEventId() {
        return this.eventId;
    }

    public LocalDate getCouponDeadline() {
        return this.couponDeadline;
    }

    public int getDiscountRate() {
        return this.discountRate;
    }

    public long getDiscountPrice() {
        return this.discountPrice;
    }

    public int getCouponQuantity() {
        return this.couponQuantity;
    }
}