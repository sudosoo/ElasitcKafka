package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.dto.category.UpdateCategoryRequestDto
import com.sudosoo.takeItEasy.domain.entity.Category
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun create(requestDto: CreateCategoryRequestDto)
    fun updateTitle(requestDto: UpdateCategoryRequestDto)
    fun getById(categoryId: Long): Category

}
