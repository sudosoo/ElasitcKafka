package com.sudosoo.takeiteasy.dto.event;

import com.sudosoo.takeiteasy.common.annotation.CustomDateTimeFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateEventRequestDto {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(max = 100,message = "제목은 100자를 넘을 수 없습니다.")
    private String eventName;

    @CustomDateTimeFormat
    private String eventDeadline;

    @CustomDateTimeFormat
    private String couponDeadline;

    private int couponQuantity;

    @Column(length = 100)
    private int discountRate;

    private long discountPrice;

    public CreateEventRequestDto(String eventName, String eventDeadline, String couponDeadline, int couponQuantity, long discountPrice) {
        this.eventName = eventName;
        this.eventDeadline = eventDeadline;
        this.couponDeadline = couponDeadline;
        this.couponQuantity = couponQuantity;
        this.discountPrice = discountPrice;
    }

    public CreateEventRequestDto(String eventName, String eventDeadline, String couponDeadline, int couponQuantity, int discountRate) {
        this.eventName = eventName;
        this.eventDeadline = eventDeadline;
        this.couponDeadline = couponDeadline;
        this.couponQuantity = couponQuantity;
        this.discountRate = discountRate;
    }
}