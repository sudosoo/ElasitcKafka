package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.*;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class HeartServiceImplTest {
    PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);
    CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
    HeartRepository heartRepository = mock(HeartRepository.class);
    PostService postService = mock(PostService.class);
    MemberService memberService = mock(MemberService.class);
    CommentService commentService = mock(CommentService.class);
    HeartService heartService = new HeartServiceImpl(heartRepository,memberService,postService,commentService);

    @BeforeEach
    void setUp() {
        Member mockMember = mock(Member.class);
        Post mockPost = mock(Post.class);
        Comment mockComment = mock(Comment.class);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(mockMember);
        when(postService.getPostByPostId(anyLong())).thenReturn(mockPost);
        when(commentService.getCommentByCommentId(anyLong())).thenReturn(mockComment);
    }

    @Test
    @DisplayName("createdPostHeart")
    void createdPostHeart(){
        //given
        Member mockMember = mock(Member.class);
        Post mockPost = mock(Post.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mockPost,mockMember));
        when(heartRepository.findByMemberAndPost(any(Member.class),any(Post.class)))
                .thenReturn(Optional.ofNullable(Heart.getPostHeart(mockPost, mockMember)));

        //when
        Heart heart = heartService.createdPostHeart(postHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
        assertDoesNotThrow(() -> heartService.createdPostHeart(postHeartRequestDto));
        verify(heartRepository, times(2)).save(any(Heart.class));
    }

   @Test
   @DisplayName("createdCommentHeart")
   void createdCommentHeart() throws Exception {
       //given
       Member mockMember = mock(Member.class);
       Comment mockComment = mock(Comment.class);
       when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mockComment,mockMember));
       when(heartRepository.findByMemberAndComment(any(Member.class),any(Comment.class)))
               .thenReturn(Optional.ofNullable(Heart.getCommentHeart(mockComment, mockMember)));

       //when
       Heart heart = heartService.createdCommentHeart(commentHeartRequestDto);

       //then
       assertNotNull(heart, "The actual heart should not be null");
       assertDoesNotThrow(() -> heartService.createdCommentHeart(commentHeartRequestDto));
       verify(heartRepository, times(2)).save(any(Heart.class));
   }

    @Test
    @DisplayName("postDisHeart")
    void postDisHeart() throws Exception {
        //given
        Member mockMember = mock(Member.class);
        Post mockPost = mock(Post.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mockPost,mockMember));
        when(heartRepository.findByMemberAndPost(any(Member.class),any(Post.class)))
                .thenReturn(Optional.ofNullable(Heart.getPostHeart(mockPost, mockMember)));

        //when
        assertDoesNotThrow(() -> heartService.postDisHeart(postHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }

    @Test
    @DisplayName("commentDisHeart")
    void commentDisHeart() throws Exception {
        //given
        Member mockMember = mock(Member.class);
        Comment mockComment = mock(Comment.class);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mockComment,mockMember));
        when(heartRepository.findByMemberAndComment(any(Member.class),any(Comment.class)))
                .thenReturn(Optional.ofNullable(Heart.getCommentHeart(mockComment, mockMember)));

        //when
        assertDoesNotThrow(() -> heartService.commentDisHeart(commentHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }
}