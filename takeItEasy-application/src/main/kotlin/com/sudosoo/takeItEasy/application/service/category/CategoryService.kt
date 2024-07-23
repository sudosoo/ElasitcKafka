package com.sudosoo.takeItEasy.application.service.category

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.dto.category.UpdateCategoryRequestDto
import com.sudosoo.takeItEasy.domain.entity.Category
import com.sudosoo.takeItEasy.domain.repository.CategoryRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService(
    private val categoryRepository : CategoryRepository,
): JpaService<Category, Long> {
    override var jpaRepository: BaseRepository<Category, Long> = categoryRepository

    fun create(requestDto: CreateCategoryRequestDto){
        save(Category(requestDto.title))
    }

    fun updateTitle(requestDto: UpdateCategoryRequestDto) {
        findById(requestDto.categoryId)
            .apply {
                updateTitle(requestDto.title)
                save(this)
            }
    }

    fun getById(categoryId: Long): Category {
        return findById(categoryId)
    }

}