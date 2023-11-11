package com.sudosoo.takeiteasy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMemberRequestDto {
    @NotNull(message = "사용자명을 입력해 주세요.")
    private String memberName;
}
