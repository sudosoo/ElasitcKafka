package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);
    private final CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
    Long testMemberId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Post mockPost = mock(Post.class);
        when(postService.getPostByPostId(anyLong())).thenReturn(mockPost);
        Comment mockComment = mock(Comment.class);
        when(commentService.getCommentByCommentId(anyLong())).thenReturn(mockComment);
    }

    @Test
    @DisplayName("createdPostHeart")
    void createdPostHeart(){
        //given
        Post mockPost = mock(Post.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mockPost,testMemberId));
        when(heartRepository.findByMemberIdAndPost(anyLong(),any(Post.class)))
                .thenReturn(Optional.empty());

        //when
        Heart heart = heartService.createdPostHeart(postHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
        assertDoesNotThrow(() -> heartService.createdPostHeart(postHeartRequestDto));
    }

   @Test
   @DisplayName("createdCommentHeart")
   void createdCommentHeart() throws Exception {
       //given
       Comment mockComment = mock(Comment.class);
       when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mockComment,testMemberId));
       when(heartRepository.findByMemberIdAndComment(anyLong(),any(Comment.class)))
               .thenReturn(Optional.empty());

       //when
       Heart heart = heartService.createdCommentHeart(commentHeartRequestDto);

       //then
       assertNotNull(heart, "The actual heart should not be null");
       assertDoesNotThrow(() -> heartService.createdCommentHeart(commentHeartRequestDto));
   }

    @Test
    @DisplayName("postDisHeart")
    void postDisHeart() throws Exception {
        //given
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