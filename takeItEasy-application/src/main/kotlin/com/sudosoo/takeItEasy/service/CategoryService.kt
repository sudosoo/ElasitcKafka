package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeiteasy.entity.Category
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun createCategory(createCategoryRequestDto: CreateCategoryRequestDto): Category
    fun getById(categoryId: Long): Category
    fun getPostsByCategoryId(categoryId: Long, pageable: Pageable): CategoryResponseDto
}
