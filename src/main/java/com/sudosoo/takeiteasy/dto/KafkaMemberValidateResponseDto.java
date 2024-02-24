package com.sudosoo.takeiteasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KafkaMemberValidateResponseDto {
    private Long memberId;
    private String memberName;
}
