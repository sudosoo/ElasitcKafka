package com.sudosoo.takeiteasy.dto.event;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseDto {
    private Long eventId;
    private Long couponId;
}
