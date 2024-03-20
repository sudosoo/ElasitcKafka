package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private LocalDateTime couponDeadline;

    private int discountRate;

    private long discountPrice;

    private Boolean useCheck = false;

    @Builder
    private Coupon(String couponName, LocalDateTime couponDeadline, int discountRate, long discountPrice, Boolean useCheck) {
        this.couponName = couponName;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.useCheck = useCheck;
    }
    public static Coupon priceOf(String couponName, String couponDeadline, long discountPrice){
        return Coupon.builder()
                .couponName(couponName+discountPrice)
                .couponDeadline(LocalDateTime.parse(couponDeadline))
                .discountPrice(discountPrice)
                .build();
    }
    public static Coupon rateOf(String couponName, String couponDeadline, int discountRate){
        return Coupon.builder()
                .couponName(couponName+discountRate)
                .couponDeadline(LocalDateTime.parse(couponDeadline))
                .discountRate(discountRate)
                .build();
    }

}