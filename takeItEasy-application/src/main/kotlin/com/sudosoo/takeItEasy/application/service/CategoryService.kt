package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.domain.entity.Category
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun createCategory(createCategoryRequestDto: CreateCategoryRequestDto): Category
    fun getById(categoryId: Long): Category
    fun getPostsByCategoryId(categoryId: Long, pageable: Pageable): CategoryResponseDto
}
