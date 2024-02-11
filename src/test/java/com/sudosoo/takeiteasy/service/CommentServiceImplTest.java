package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private CommentServiceImpl commentService;
    private CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");

    private Comment testComment = Comment.of(createCommentRequestDto);
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Member mockMember = mock(Member.class);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(mockMember);

        Post mockPost = mock(Post.class);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPost));


    }

    @Test
    @DisplayName("createComment")
    void createComment() {
        //given
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        //when
        Comment resultComment = commentService.createComment(createCommentRequestDto);

        //then
        verify(commentRepository,times(1)).save(any(Comment.class));
        assertEquals(testComment.getId(),resultComment.getId());
    }

    @Test
    void getCommentByCommentId() {
        //given
        when(commentRepository.findById(any())).thenReturn(Optional.of(testComment));

        //when
        Comment resultComment = commentService.getCommentByCommentId(1L);

        //then
        String expectedCommentContent = createCommentRequestDto.getContent();
        String actualCommentContent = resultComment.getContent();

        verify(commentRepository,times(1)).findById(1L);
        assertEquals(expectedCommentContent, actualCommentContent, "Expected Comment Content: " + expectedCommentContent + ", Actual Comment Content: " + actualCommentContent);
    }
}