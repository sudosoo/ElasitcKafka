package com.sudosoo.takeiteasy.application.service;

import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto;
import com.sudosoo.takeItEasy.application.dto.post.PostDetailResponseDto;
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer;
import com.sudosoo.takeItEasy.application.redis.RedisService;
import com.sudosoo.takeItEasy.application.service.CategoryService;
import com.sudosoo.takeItEasy.application.service.PostServiceImpl;
import com.sudosoo.takeItEasy.domain.entity.Comment;
import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.CommentRepository;
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
import static org.mockito.Mockito.*;

class PostServiceImplTest {
    @Mock
    CategoryService categoryService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    KafkaProducer kafkaProducer;
    @Mock
    RedisService redisService;

    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostServiceImpl postService;

    private final CreatePostRequestDto testRequestDto = new CreatePostRequestDto("제목","내용",1L,1L);
    private final Post testPost = Post.of(testRequestDto.getTitle(), testRequestDto.getContent());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(postRepository.save(any(Post.class))).thenReturn(testPost);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testPost));
    }
//
//    @Test
//    @DisplayName("createPost")
//    void createPost() {
//        //given
//        Category categoryMock = mock(Category.class);
//        when(categoryService.getCategoryByCategoryId(anyLong())).thenReturn(categoryMock);
//
//        //when
//        Post post = postService.create(testRequestDto);
//
//        //then
//        String expectedTitle = testRequestDto.getTitle();
//        String actualTitle = post.getTitle();
//
//        assertNotNull(post, "The created post should not be null");
//        assertEquals(expectedTitle, actualTitle, "Expected Title: " + expectedTitle + ", Actual Title: " + actualTitle);
//    }

    @Test
    @DisplayName("getPostByPostId")
    void getPostByPostId() {
        //given
        //when
        Post testPost = postService.getByPostId(1L);

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
        Comment commentMock1 = Comment.of("test1");
        Comment commentMock2 = Comment.of("test2");
        Comment commentMock3 = Comment.of("test3");
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Comment> commentPage = new PageImpl<>(Arrays.asList(commentMock1,commentMock2,commentMock3));
        when(commentRepository.findCommentsByPostId(1L, pageRequest)).thenReturn(commentPage);

        // when
        PostDetailResponseDto result = postService.getPostDetailByPostId(1L, pageRequest);

        // then
        assertEquals(commentPage.getContent().size(), result.getComments().size());
    }

}