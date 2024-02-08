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
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Member mockMember = mock(Member.class);
        when(memberService.getMemberByMemberId(anyLong())).thenReturn(mockMember);

        Post mockPost = mock(Post.class);
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPost));
    }

    @Test
    @DisplayName("createdComment")
    void createComment() {
        //given
        CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");
        when(commentService.createComment(createCommentRequestDto)).thenReturn(Comment.of(createCommentRequestDto));

        //when
        Comment testComment = commentService.createComment(createCommentRequestDto);

        //then
        String expectedContent = createCommentRequestDto.getContent();
        String actualContent = testComment.getContent();

        assertNotNull(testComment,"The created comment should not be null");
        assertEquals(expectedContent, actualContent, "Expected Content: " + expectedContent + ", Actual Content: " + actualContent);
    }

    @Test
    void getCommentByCommentId() {
        //given
        CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");
        when(commentRepository.findById(any())).thenReturn(Optional.of(Comment.of(createCommentRequestDto)));

        //when
        Comment testComment = commentService.getCommentByCommentId(1L);

        //then
        String expectedCommentContent = createCommentRequestDto.getContent();
        String actualCommentContent = testComment.getContent();

        assertNotNull(testComment, "The actual comment should not be null");
        assertEquals(expectedCommentContent, actualCommentContent, "Expected Comment Content: " + expectedCommentContent + ", Actual Comment Content: " + actualCommentContent);
    }
}