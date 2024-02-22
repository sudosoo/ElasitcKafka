package com.sudosoo.takeiteasy.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class kafkaMemberValidateRequestDto {
    private String targetMethod = "validateMemberId";
    private final Long memberId;
}
