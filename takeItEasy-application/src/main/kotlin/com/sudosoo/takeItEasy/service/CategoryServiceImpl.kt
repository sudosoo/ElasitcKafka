package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeiteasy.entity.Category
import com.sudosoo.takeiteasy.entity.Post
import com.sudosoo.takeiteasy.repository.CategoryRepository
import com.sudosoo.takeiteasy.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(
    val categoryRepository : CategoryRepository,
    val postRepository : PostRepository ) : CategoryService{
    override fun createCategory(createCategoryRequestDto: CreateCategoryRequestDto): Category {
        val category = Category.of(createCategoryRequestDto)

        return categoryRepository.save<Category>(category)
    }

    override fun getById(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow{ IllegalArgumentException("Could not found category id : $categoryId")}
    }

    @Transactional(readOnly = true)
    override fun getPostsByCategoryId(categoryId: Long, pageable: Pageable): CategoryResponseDto {
        val category: Category = categoryRepository.findById(categoryId).orElseThrow{ IllegalArgumentException("해당 카테고리가 존재하지 않습니다") })

        val paginatedPost: Page<Post> = postRepository.findPostsPaginationByCategoryId(categoryId, pageable)
        val responsePosts : MutableList<PostTitleOnlyResponseDto> =
            paginatedPost.stream().map{ o -> o.toTitleOnlyDto()}.toList()

        return category.toResponseDto(category, PageImpl(responsePosts))
    }
}
