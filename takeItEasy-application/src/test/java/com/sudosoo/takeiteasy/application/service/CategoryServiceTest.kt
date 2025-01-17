package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.service.category.CategoryService
import com.sudosoo.takeItEasy.domain.entity.Category
import com.sudosoo.takeItEasy.domain.repository.category.CategoryRepository
import com.sudosoo.takeItEasy.domain.repository.post.PostRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.*
import java.util.*

class CategoryServiceTest {
    @Mock
    lateinit var categoryRepository: CategoryRepository
    @Mock
    lateinit var postRepository: PostRepository
    @Mock
    lateinit var jpaService: JpaService<Category, Long>
    @InjectMocks
    lateinit var categoryService: CategoryService

    private val createCategoryRequestDto = CreateCategoryRequestDto("test 카테고리")
    private val testCategory: Category = Category(createCategoryRequestDto.title)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun `카테고리 만들기`() {
        //given
        val createCategoryRequestDto = CreateCategoryRequestDto("Test 카테고리")
        `when`(categoryRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.ofNullable(testCategory))
        `when`(categoryRepository.save(any())).thenReturn(testCategory)

        //when
        categoryService.create(createCategoryRequestDto)

        //then
        verify(categoryRepository, times(1)).save(any())
    }

    @Test
    fun `카테고리 아이디로 카테고리 가져오기`() {
        //given
        `when`(categoryRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.ofNullable(testCategory))

        //when
        val actualCategory = categoryService.getById(1L)

        //then
        val expectedName = testCategory.title
        val actualName = actualCategory.title

        Assertions.assertNotNull(actualCategory, "The created post should not be null")
        Assertions.assertEquals(
            expectedName, actualName,
            "Expected Title: $expectedName, Actual Title: $actualName"
        )
    }

}