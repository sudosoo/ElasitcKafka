package com.sudosoo.takeiteasy.application.service;

import com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto;
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeItEasy.application.service.CategoryServiceImpl;
import com.sudosoo.takeItEasy.domain.entity.Category;
import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.CategoryRepository;
import com.sudosoo.takeItEasy.domain.repository.PostRepository;
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
import static org.mockito.Mockito.*;


class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private final CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto("Test카테고리");
    private Category testCategory = Category.of(createCategoryRequestDto.getCategoryName());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @DisplayName("createCategory")
    void createCategory() throws Exception {
        //given
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto("Test 카테고리");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testCategory));

        //when
        categoryService.create(createCategoryRequestDto);

        //then
        verify(categoryRepository,times(1)).save(testCategory);
    }
    @Test
    @DisplayName("getCategoryByCategoryId")
    void getCategoryByCategoryId() {
        //given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testCategory));

        //when
        Category actualCategory = categoryService.getById(1L);

        //then
        String expectedName = testCategory.getCategoryName();
        String actualName = actualCategory.getCategoryName();

        assertNotNull(actualCategory, "The created post should not be null");
        assertEquals(expectedName, actualName, "Expected Title: " + expectedName + ", Actual Title: " + actualName);
    }
//    @Test
//    @DisplayName("getPostsByCategoryId")
//    void getPostsByCategoryId(){
//        //given
//        Post postMock1 = mock(Post.class);
//        Post postMock2 = mock(Post.class);
//        Post postMock3 = mock(Post.class);
//        Pageable pageRequest = PageRequest.of(0, 10);
//        Page<Post> postsPage = new PageImpl<>(Arrays.asList(postMock1,postMock2,postMock3));
//        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(testCategory));
//        when(postRepository.findPostsPaginationByCategoryId(1L, pageRequest)).thenReturn(postsPage);
//
//        //when
//        CategoryResponseDto result = categoryService.getPosts(1L, PageRequest.of(0, 10));
//
//        //then
//        assertEquals(testCategory.getCategoryName(), result.getCategoryName());
//    }
}