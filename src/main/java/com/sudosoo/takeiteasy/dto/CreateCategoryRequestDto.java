package com.sudosoo.takeiteasy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCategoryRequestDto {
    @NotNull(message = "카테고리 이름을 입력해 주세요.")
    private String categoryName ;

}
