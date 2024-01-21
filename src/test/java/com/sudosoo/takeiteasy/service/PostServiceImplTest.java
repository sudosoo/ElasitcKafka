package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.entity.*;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    MemberService memberService = mock(MemberService.class);
    CategoryService categoryService = mock(CategoryService.class);
    PostRepository postRepository = mock(PostRepository.class);
    CommentRepository commentRepository = mock(CommentRepository.class);
    PostService postService = new PostServiceImpl(commentRepository,postRepository,categoryService,memberService);
    Heart heartMock = mock(Heart.class);
    Category categoryMock = mock(Category.class);
    Member memberMock = mock(Member.class);
    CreatePostRequestDto createPostRequestDto;
    Post testPost;

    @BeforeEach
    void setUp() {
        when(memberService.getMemberByMemberId(createPostRequestDto.getMemberId())).thenReturn(memberMock);
        when(postRepository.save(any(Post.class))).thenReturn(testPost);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(Post.of(createPostRequestDto)));
        createPostRequestDto = new CreatePostRequestDto("제목","내용",1L,1L);
        testPost = Post.testOf(1L,"제목","내용",categoryMock,memberMock,0,List.of(heartMock));

    }

    @Test
    @DisplayName("createPost")
    void createPost() {
        //given
        Category categoryMock = mock(Category.class);
        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);

        //when
        Post post = postService.createdPost(createPostRequestDto);

        //then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = post.getTitle();

        assertNotNull(post, "The created post should not be null");
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
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(testPost));
        when(commentRepository.findCommentsByPostId(1L, pageRequest)).thenReturn(commentPage);

        // when
        PostDetailResponseDto result = postService.getPostDetailByPostId(1L, PageRequest.of(0, 10));

        // then
        assertEquals(testPost.getId(), result.getPostId());
        assertEquals(testPost.getTitle(), result.getTitle());
        assertEquals(testPost.getContent(), result.getContent());
        assertEquals(commentPage.getSize(), result.getCommentsResponseDto().getSize());
    }

}