package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.domain.entity.Category
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.CategoryRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(
    val categoryRepository : CategoryRepository,
    val postRepository : PostRepository ) :CategoryService, JpaService<Category, Long> {

    override var jpaRepository: JpaRepository<Category, Long> = categoryRepository

    override fun create(requestDto: CreateCategoryRequestDto): Category {
        val category = Category(requestDto.categoryName)

        return save(category)
    }

    override fun getById(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow{ IllegalArgumentException("Could not found category id : $categoryId")}
    }

    @Transactional(readOnly = true)
    override fun getPosts(categoryId: Long, pageable: Pageable): com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto {
        val category: Category = categoryRepository.findById(categoryId).orElseThrow{ IllegalArgumentException("해당 카테고리가 존재하지 않습니다") }

        val paginatedPost: Page<Post> = postRepository.findPostsPaginationByCategoryId(categoryId, pageable)
        val responsePosts : MutableList<PostTitleOnlyResponseDto> =
            paginatedPost.stream().map{ o -> PostTitleOnlyResponseDto(o)}.toList()

        return CategoryResponseDto(category, PageImpl(responsePosts))
    }

}
