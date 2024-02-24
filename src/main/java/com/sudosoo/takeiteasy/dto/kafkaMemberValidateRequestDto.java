package com.sudosoo.takeiteasy.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class kafkaMemberValidateRequestDto {
    private final Long memberId;
}
