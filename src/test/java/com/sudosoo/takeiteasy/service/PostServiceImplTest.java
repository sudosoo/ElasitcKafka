package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    MemberService memberService = mock(MemberService.class);
    PostRepository postRepository = mock(PostRepository.class);
    CategoryService categoryService = mock(CategoryService.class);
    PostServiceImpl postService = new PostServiceImpl(postRepository,categoryService,memberService);
    CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("TestTitle","TestContent",1L,null);

    @BeforeEach
    void setUp() {
        CreateMemberRequestDto mockMember = new CreateMemberRequestDto("TestMember");
        when(memberService.getMemberByMemberId(createPostRequestDto.getMemberId())).thenReturn(Member.getInstance(mockMember));
        when(postRepository.save(any(Post.class))).thenReturn(Post.getInstance(createPostRequestDto));
    }

    @Test
    @DisplayName("createPostWithoutCategory")
    void creatPost() {
        // Given
        CreatePostRequestDto requestDto = new CreatePostRequestDto("TestTitle", "TestContent", 1L, null);
        Member member = memberService.getMemberByMemberId(requestDto.getMemberId());

        // When
        Post createdPost = postService.creatPost(requestDto);
        createdPost.setMember(member);

        // Then
        String expectedTitle = requestDto.getTitle();
        String actualTitle = createdPost.getTitle();

        assertNotNull(createdPost,"The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
        verify(categoryService, Mockito.never()).getCategoryByCategoryId(any());
    }

    @Test
    @DisplayName("createPostWithCategory")
    void createPostWithCategory() {
        // given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("TestTitle", "TestContent", 1L, 1L);
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto("TestCategory");
        when(categoryService.getCategoryByCategoryId(createPostRequestDto.getCategoryId())).thenReturn(Category.of(createCategoryRequestDto));
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());

        // when
        Post createdPost = postService.creatPost(createPostRequestDto);
        createdPost.setMember(member);

        // then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = createdPost.getTitle();

        assertNotNull(createdPost,"The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
        verify(categoryService, Mockito.times(1)).getCategoryByCategoryId(createPostRequestDto.getCategoryId());
    }

    @Test
    @DisplayName("getPostByPostId")
    void getPostByPostId() {
        // Given
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(Post.getInstance(createPostRequestDto)));
        Post expectedPost = Post.getInstance(createPostRequestDto);

        // When
        Post createdPost = postService.creatPost(createPostRequestDto);

        // Then
        String expectedTitle = expectedPost.getTitle();
        String actualTitle = createdPost.getTitle();

        assertNotNull(createdPost, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
    }
}