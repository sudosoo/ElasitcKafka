package com.sudosoo.takeItEasy.dto.category

import com.sudosoo.takeItEasy.dto.post.PostTitleOnlyResponseDto
import org.springframework.data.domain.Page

class CategoryResponseDto(
    val categoryName: String,
    val postTitleDtos: Page<PostTitleOnlyResponseDto>
)