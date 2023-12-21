package com.sudosoo.takeiteasy.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateCategoryRequestDto {
    @NotNull(message = "카테고리 이름을 입력해 주세요.")
    private String categoryName ;

}
