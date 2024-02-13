package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @Mock
    private MemberService memberService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private Heart heartMock;
    @Mock
    private Category categoryMock;
    @Mock
    private Member memberMock;
    private Post testPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testPost = Post.testOf(1L, "제목", "내용", categoryMock, memberMock, 0, List.of(heartMock));
    }

    @Test
    @DisplayName("createPost")
    void createPost() {
        // given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("제목", "내용", 1L, 1L);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(memberMock);
        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // when
        Post post = postService.createPost(createPostRequestDto);

        // then
        String expectedTitle = createPostRequestDto.getTitle();
        String actualTitle = post.getTitle();

        assertNotNull(post, "The created post should not be null");
        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
    }

    @Test
    @DisplayName("getPostDetailByPostId")
    void getPostDetailByPostId() {
        // given
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Comment> commentPage = new PageImpl<>(Arrays.asList(mock(Comment.class), mock(Comment.class), mock(Comment.class)));
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(testPost));
        when(commentRepository.findCommentsByPostId(1L, pageRequest)).thenReturn(commentPage);

        // when
        PostDetailResponseDto result = postService.getPostDetailByPostId(1L, pageRequest);

        // then
        assertEquals(testPost.getId(), result.getPostId());
        assertEquals(commentPage.getSize(), result.getCommentsResponseDto().getSize());
    }

    @Test
    @DisplayName("getPaginationPost")
    void getPaginationPost() throws Exception {
        //given
        Pageable pageRequest = PageRequest.of(0,10);
        List<Post> testPostList = new ArrayList<>();
        Post testPost1 = Post.testOf(1L, "제목1", "내용1", categoryMock, memberMock, 0, List.of(heartMock));
        Post testPost2 = Post.testOf(2L, "제목2", "내용2", categoryMock, memberMock, 0, List.of(heartMock));
        Post testPost3 = Post.testOf(3L, "제목3", "내용3", categoryMock, memberMock, 0, List.of(heartMock));
        testPostList.add(testPost1);
        testPostList.add(testPost2);
        testPostList.add(testPost3);

        Page<Post> paginationPost = new PageImpl<>(testPostList);
        when(postRepository.findAll(pageRequest)).thenReturn(paginationPost);

        //when
        List<PostTitleOnlyResponseDto> actualPost= postService.getPaginationPost(pageRequest);

        //then
        assertEquals(3, actualPost.size());
        assertEquals("제목1", actualPost.get(0).getTitle());

        verify(postRepository, times(1)).findAll(pageRequest);
    }
}