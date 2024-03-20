package com.sudosoo.takeItEasy.application.dto.category

import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.domain.entity.Category
import org.springframework.data.domain.Page

class CategoryResponseDto(
    val categoryName: String,
    val postTitleDtos: Page<PostTitleOnlyResponseDto>
){
    fun toResponseDto(category: Category, postTitleDtoPage: Page<PostTitleOnlyResponseDto>): CategoryResponseDto {
        return CategoryResponseDto(category.categoryName, postTitleDtoPage!!)
    }
}