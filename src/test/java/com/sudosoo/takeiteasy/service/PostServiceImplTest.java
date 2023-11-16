package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    MemberService memberService = mock(MemberService.class);
    PostRepository postRepository = mock(PostRepository.class);
    CategoryService categoryService = mock(CategoryService.class);
    PostService postService = new PostServiceImpl(postRepository,categoryService,memberService);
    CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("TestTitle","TestContent",1L,null);
    @BeforeEach
    void setUp() {
        Member memberMock = mock(Member.class);
        when(memberService.getMemberByMemberId(createPostRequestDto.getMemberId())).thenReturn(memberMock);
        when(postRepository.save(any(Post.class))).thenReturn(Post.of(createPostRequestDto));
    }

    @Test
    @DisplayName("createPostWithoutCategory")
    void creatPost() {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("TestTitle", "TestContent", 1L, null);

        //when
        Post testPost = postService.createdPost(createPostRequestDto);

        //then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = testPost.getTitle();

        assertNotNull(testPost, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
        verify(categoryService,never()).getCategoryByCategoryId(anyLong());
        verify(postRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("createPostWithCategory")
    void createPostWithCategory() {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("TestTitle", "TestContent", 1L, 1L);
        Category categoryMock = mock(Category.class);
        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);

        //when
        Post testPost = postService.createdPost(createPostRequestDto);

        //then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = testPost.getTitle();

        assertNotNull(testPost, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
        verify(categoryService,times(1)).getCategoryByCategoryId(eq(1L));
        verify(postRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("getPostByPostId")
    void getPostByPostId() {
        //given
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Post.of(createPostRequestDto)));

        //when
        Post testPost = postService.getPostByPostId(1L);

        //then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = testPost.getTitle();

        assertNotNull(testPost, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
        verify(postRepository, times(1)).findById(eq(1L));
    }
}