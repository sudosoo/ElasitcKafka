package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.CommonService;
import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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

    Post mockPost;
    Comment mockComment;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockPost = mock(Post.class);
        when(postService.getByPostId(anyLong())).thenReturn(mockPost);
        mockComment = mock(Comment.class);
        when(commentService.getByCommentId(anyLong())).thenReturn(mockComment);
    }

    @Test
    @DisplayName("createPostHeart")
    void createPostHeart(){
        //given
        PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);

        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mockPost, anyLong()));
        when(heartRepository.existsByMemberIdAndPost(anyLong(),mockPost))
                .thenReturn(false);
        //when
        Heart heart = heartService.createPostHeart(postHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
        assertDoesNotThrow(() -> heartService.createPostHeart(postHeartRequestDto));
    }


    @Test
    @DisplayName("createCommentHeart")
    void createCommentHeart() {
        //given
        CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mockComment,anyLong()));
        when(heartRepository.existsByMemberIdAndComment(anyLong(),mockComment))
                .thenReturn(false);
        //when
        Heart heart = heartService.createCommentHeart(commentHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
    }

    @Test
    @DisplayName("postDisHeart")
    void postDisHeart() {
        //given
        Heart postHeart = Heart.getPostHeart(mockPost, testMemberId);
        PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);
        when(heartRepository.findByMemberIdAndPost(testMemberId, mockPost))
                .thenReturn(Optional.ofNullable(postHeart));
        when(postService.getByPostId(anyLong())).thenReturn(mockPost);

        //static method mocking
        try{
            MockedStatic<CommonService> mockService = mockStatic(CommonService.class);
            mockService.when(() -> doNothing().when(CommonService.checkNotNullData(any(), any()))).thenCallRealMethod();
            mockService.close();
        }catch (Exception e){

        }
        //when
        assertDoesNotThrow(() -> heartService.postDisHeart(postHeartRequestDto));

        //then
        verify(heartRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("commentDisHeart")
    void commentDisHeart() {
        //given
        Heart commentHeart = Heart.getCommentHeart(mockComment, testMemberId);
        CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);
        when(heartRepository.save(any(Heart.class))).thenReturn(commentHeart);
        when(heartRepository.findByMemberIdAndComment(testMemberId,mockComment))
                .thenReturn(Optional.ofNullable(commentHeart));
        //static method mocking
        try{
            MockedStatic<CommonService> mockService = mockStatic(CommonService.class);
            mockService.when(() -> doNothing().when(CommonService.checkNotNullData(any(), any()))).thenCallRealMethod();
            mockService.close();
        }catch (Exception e){
        }

        //when
        assertDoesNotThrow(() -> heartService.commentDisHeart(commentHeartRequestDto));

        //then
        verify(heartRepository, times(1)).deleteById(any());
    }
}