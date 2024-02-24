package com.sudosoo.takeiteasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class KafkaMemberValidateResponseDto {
    private Long memberId;
    private String memberName;
}
