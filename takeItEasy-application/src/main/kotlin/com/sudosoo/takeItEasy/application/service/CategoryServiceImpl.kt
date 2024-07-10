package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.dto.category.UpdateCategoryRequestDto
import com.sudosoo.takeItEasy.domain.entity.Category
import com.sudosoo.takeItEasy.domain.repository.CategoryRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(
    private val categoryRepository : CategoryRepository,
    private val postRepository : PostRepository
) :CategoryService, JpaService<Category, Long> {
    override var jpaRepository: BaseRepository<Category, Long> = categoryRepository

    override fun create(requestDto: CreateCategoryRequestDto){
        save(Category(requestDto.title))
    }

    override fun updateTitle(requestDto: UpdateCategoryRequestDto) {
        categoryRepository.findById(requestDto.categoryId)
            .orElseThrow{ IllegalArgumentException("Could not found category id : ${requestDto.categoryId}")}
            .apply {
                updateTitle(requestDto.title)
                save(this)
            }
    }

    override fun getById(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow{ IllegalArgumentException("Could not found category id : $categoryId")}
    }

}
