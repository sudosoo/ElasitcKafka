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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "coupon_name")
    private String couponName;

    @Column(nullable = false,name = "coupon_deadline")
    private LocalDateTime couponDeadline;

    private int discountRate;

    private long discountPrice;

    private boolean useCheck = false;

    @Builder
    private Coupon(String couponName, LocalDateTime couponDeadline, int discountRate, long discountPrice, Boolean useCheck) {
        this.couponName = couponName;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.useCheck = useCheck;
    }
    public static Coupon priceOf(CreateEventRequestDto requestDto,LocalDateTime couponDeadline){
        return Coupon.builder()
                .couponName(requestDto.getEventName()+requestDto.getDiscountPrice())
                .couponDeadline(couponDeadline)
                .discountPrice(requestDto.getDiscountPrice())
                .build();
    }
    public static Coupon rateOf(CreateEventRequestDto requestDto,LocalDateTime couponDeadline){
        return Coupon.builder()
                .couponName(requestDto.getEventName()+requestDto.getDiscountRate())
                .couponDeadline(couponDeadline)
                .discountRate(requestDto.getDiscountRate())
                .build();
    }

}