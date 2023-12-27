package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import io.micrometer.core.instrument.util.AbstractPartition;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private int discountRate;

    private int discountPrice;

    private Boolean useCheck = false;
    @Builder
    public Coupon(Long id, String couponName, LocalDateTime couponDeadline, int discountRate, int discountPrice) {
        this.id = id;
        this.couponName = couponName;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
    }
}