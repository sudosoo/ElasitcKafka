package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private LocalDateTime couponDeadline;

    private int discountRate = 0;

    private long discountPrice = 0;

    private Boolean useCheck = false;

    @Builder
    private Coupon(String couponName, LocalDateTime couponDeadline, int discountRate, long discountPrice, Boolean useCheck) {
        this.couponName = couponName;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.useCheck = useCheck;
    }
    public static Coupon priceOf(CreateEventRequestDto requestDto){
        return Coupon.builder()
                .couponName(requestDto.getEventName()+requestDto.getDiscountPrice())
                .couponDeadline(LocalDateTime.parse(requestDto.getCouponDeadline()))
                .discountPrice(requestDto.getDiscountPrice())
                .build();
    }
    public static Coupon rateOf(CreateEventRequestDto requestDto){
        return Coupon.builder()
                .couponName(requestDto.getEventName()+requestDto.getDiscountRate())
                .couponDeadline(LocalDateTime.parse(requestDto.getCouponDeadline()))
                .discountRate(requestDto.getDiscountRate())
                .build();
    }

}