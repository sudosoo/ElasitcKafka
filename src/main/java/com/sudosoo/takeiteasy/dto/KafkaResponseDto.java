package com.sudosoo.takeiteasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class KafkaResponseDto {
    private Long memberId;
    private String memberName;

    public KafkaResponseDto(Long memberId) {
        this.memberId = memberId;
    }
}
