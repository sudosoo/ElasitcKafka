package com.sudosoo.takeItEasy.application.dto.category

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreateCategoryRequestDto(
    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    @field:Size(max = 20, message = "카테고리 이름은 20자를 넘을 수 없습니다.")
    val title : String)
