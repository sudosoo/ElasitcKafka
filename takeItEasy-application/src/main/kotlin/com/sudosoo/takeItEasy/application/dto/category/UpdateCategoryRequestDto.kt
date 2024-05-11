package com.sudosoo.takeItEasy.application.dto.category

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UpdateCategoryRequestDto(
    val categoryId : Long,
    @NotBlank(message = "수정 하실 이름을 입력해 주세요.")
    @field:Size(max = 20, message = "카테고리 이름은 20자를 넘을 수 없습니다.")
    val title : String)
