package com.sudosoo.takeiteasy.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateMemberRequestDto {

    @NotNull(message = "사용자명을 입력해 주세요.")
    private String memberName;
}
