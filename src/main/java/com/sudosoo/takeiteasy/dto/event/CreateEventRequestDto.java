package com.sudosoo.takeiteasy.dto.event;

import com.sudosoo.takeiteasy.common.CustomDateTimeFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateEventRequestDto {

    @NotNull(message = "제목을 입력해 주세요.")
    @Size(max = 100,message = "제목은 100자를 넘을 수 없습니다.")
    private String eventName;

    @CustomDateTimeFormat
    private String eventDeadline;

    @CustomDateTimeFormat
    private String couponDeadline;

    private int couponQuantity;

    private int discountRate;

    private long discountPrice;
}