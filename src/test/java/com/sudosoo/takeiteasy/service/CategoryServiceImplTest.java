package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CategoryService categoryService;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto("Test 카테고리");
        testCategory = Category.of(createCategoryRequestDto);
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @DisplayName("createCategory")
    void createCategory() throws Exception {
        //given
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto("Test 카테고리");
        Category categoryMock = mock(Category.class);
        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);

        //when
        Category category = categoryService.createCategory(createCategoryRequestDto);

        //then
        String expectedTitle = createCategoryRequestDto.getCategoryName();
        String actualTitle = category.getCategoryName();

        assertNotNull(category, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
    }
    @Test
    @DisplayName("getCategoryByCategoryId")
    void getCategoryByCategoryId() {
        //given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testCategory));

        //when
        Category actualCategory = categoryService.getCategoryByCategoryId(1L);

        //then
        String expectedName = testCategory.getCategoryName();
        String actualName = actualCategory.getCategoryName();

        assertNotNull(actualCategory, "The created post should not be null");
        assertEquals(expectedName, actualName, "Expected Title: " + expectedName + ", Actual Title: " + actualName);
    }
    @Test
    @DisplayName("getPostsByCategoryId")
    void getPostsByCategoryId(){
        //given
        Post postMock1 = mock(Post.class);
        Post postMock2 = mock(Post.class);
        Post postMock3 = mock(Post.class);
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Post> postsPage = new PageImpl<>(Arrays.asList(postMock1,postMock2,postMock3));
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(testCategory));
        when(postRepository.findPostsPaginationByCategoryId(1L, pageRequest)).thenReturn(postsPage);

        //when
        CategoryResponseDto result = categoryService.getPostsByCategoryId(1L, PageRequest.of(0, 10));

        //then
        assertEquals(testCategory.getCategoryName(), result.getCategoryName());
        assertEquals(postsPage.getSize(), result.getPostResponseDtos().getSize());
    }
}