package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
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
    private HeartRepository heartRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private CommentService commentService;
    @InjectMocks
    private HeartServiceImpl heartService;
    private final PostHeartRequestDto postHeartRequestDto = new PostHeartRequestDto(1L,1L);
    private final CommentHeartRequestDto commentHeartRequestDto = new CommentHeartRequestDto(1L,1L);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Member mockMember = mock(Member.class);
        Post mockPost = mock(Post.class);
        Comment mockComment = mock(Comment.class);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(mockMember);
        when(commentService.getCommentByCommentId(anyLong())).thenReturn(mockComment);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPost));
    }

    @Test
    @DisplayName("createPostHeart")
    void createPostHeart(){
        //given
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getPostHeart(mock(Post.class), mock(Member.class)));
        when(heartRepository.existsByMemberAndPost(mock(Member.class),mock(Post.class)))
                .thenReturn(false);

        //when
        Heart heart = heartService.createPostHeart(postHeartRequestDto);

        //then
        assertNotNull(heart, "The actual heart should not be null");
    }

    @Test
    @DisplayName("createCommentHeart")
    void createCommentHeart() throws Exception {
        //given
        when(heartRepository.save(any(Heart.class))).thenReturn(Heart.getCommentHeart(mock(Comment.class), mock(Member.class)));
        when(heartRepository.existsByMemberAndComment(mock(Member.class),mock(Comment.class)))
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
        Heart mockHeart = Heart.getPostHeart(mock(Post.class), mock(Member.class));
        when(heartRepository.save(any(Heart.class))).thenReturn(mockHeart);
        when(heartRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.ofNullable(mockHeart));

        //when
        assertDoesNotThrow(() -> heartService.postDisHeart(postHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }

    @Test
    @DisplayName("commentDisHeart")
    void commentDisHeart() throws Exception {
        //given
        Heart mockHeart = Heart.getCommentHeart(mock(Comment.class), mock(Member.class));
        when(heartRepository.save(any(Heart.class))).thenReturn(mockHeart);
        when(heartRepository.findByMemberAndComment(any(Member.class), any(Comment.class)))
                .thenReturn(Optional.ofNullable(mockHeart));

        //when
        assertDoesNotThrow(() -> heartService.commentDisHeart(commentHeartRequestDto));

        //then
        verify(heartRepository, times(1)).delete(any(Heart.class));
    }

}
