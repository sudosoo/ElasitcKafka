package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeItEasy.application.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeItEasy.application.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeItEasy.application.service.CommentService;
import com.sudosoo.takeItEasy.application.service.HeartServiceImpl;
import com.sudosoo.takeItEasy.application.service.PostService;
import com.sudosoo.takeItEasy.domain.entity.Comment;
import com.sudosoo.takeItEasy.domain.entity.Heart;
import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.HeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class HeartServiceImplTest {
    @Mock
    HeartRepository heartRepository;
    @Mock
    PostService postService;
    @Mock
    CommentService commentService;
    @InjectMocks
    HeartServiceImpl heartService;
    Long testMemberId = 1L;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Post mockPost = mock(Post.class);
        when(postService.getByPostId(anyLong())).thenReturn(mockPost);
        Comment mockComment = mock(Comment.class);
        when(commentService.getByCommentId(anyLong())).thenReturn(mockComment);
    }

    @Test
    @DisplayName("createdPostHeart")
    void createdPostHeart(){
        //given
        PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);

        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mock(Post.class), anyLong()));
        when(heartRepository.existsByMemberIdAndPost(anyLong(),mock(Post.class)))
                .thenReturn(false);
        //when
        Heart heart = heartService.createPostHeart(postHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
        assertDoesNotThrow(() -> heartService.createPostHeart(postHeartRequestDto));
    }


    @Test
    @DisplayName("createCommentHeart")
    void createCommentHeart() throws Exception {
        //given
        final CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mock(Comment.class),anyLong()));
        when(heartRepository.existsByMemberIdAndComment(anyLong(),mock(Comment.class)))
                .thenReturn(false);
        //when
        Heart heart = heartService.createCommentHeart(commentHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
    }

    @Test
    @DisplayName("postDisHeart")
    void postDisHeart() throws Exception {
        //given
        PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);
        Post mockPost = mock(Post.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mockPost,testMemberId));
        when(heartRepository.findByMemberIdAndPost(anyLong(), any(Post.class)))
                .thenReturn(Optional.ofNullable(Heart.getPostHeart(mockPost, testMemberId)));

        //when
        assertDoesNotThrow(() -> heartService.postDisHeart(postHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }

    @Test
    @DisplayName("commentDisHeart")
    void commentDisHeart() throws Exception {
        //given
        final CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
        Comment mockComment = mock(Comment.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mockComment,testMemberId));
        when(heartRepository.findByMemberIdAndComment(anyLong(),any(Comment.class)))
                .thenReturn(Optional.ofNullable(Heart.getCommentHeart(mockComment, testMemberId)));

        //when
        assertDoesNotThrow(() -> heartService.commentDisHeart(commentHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }
}