package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.entity.*;
import com.sudosoo.takeiteasy.repository.CommentRepository;
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
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    CategoryService categoryService;
    @Mock
    PostRepository postRepository;
    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    PostServiceImpl postService;
    private final CreatePostRequestDto testRequestDto = new CreatePostRequestDto("제목","내용",1L,1L);
    private final Post testPost = Post.of(testRequestDto);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(postRepository.save(any(Post.class))).thenReturn(testPost);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testPost));
    }

    @Test
    @DisplayName("createPost")
    void createPost() {
        //given
        Category categoryMock = mock(Category.class);
        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);

        //when
        Post post = postService.create(testRequestDto);

        //then
        String expectedTitle = testRequestDto.getTitle();
        String actualTitle = post.getTitle();

        assertNotNull(post, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
    }

    @Test
    @DisplayName("getPostByPostId")
    void getPostByPostId() {
        //given
        //when
        Post testPost = postService.getPostByPostId(1L);

        //then
        String expectedTitle = testRequestDto.getTitle();
        String actualTitle = testPost.getTitle();

        assertNotNull(testPost, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
    }

    @Test
    @DisplayName("getPostDetailByPostId")
    void getPostDetailByPostId() {
        // given
        Comment commentMock1 = mock(Comment.class);
        Comment commentMock2 = mock(Comment.class);
        Comment commentMock3 = mock(Comment.class);
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Comment> commentPage = new PageImpl<>(Arrays.asList(commentMock1,commentMock2,commentMock3));
        when(commentRepository.findCommentsByPostId(1L, pageRequest)).thenReturn(commentPage);

        // when
        PostDetailResponseDto result = postService.getPostDetailByPostId(1L, PageRequest.of(0, 10));

        // then
        assertEquals(testPost.getId(), result.getPostId());
        assertEquals(testPost.getTitle(), result.getTitle());
        assertEquals(testPost.getContent(), result.getNotifyContent());
        assertEquals(commentPage.getSize(), result.getCommentsResponseDto().getSize());
    }

}