package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
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
    CommentRepository commentRepository;
    @Mock
    PostService postService;
    @InjectMocks
    CommentServiceImpl commentService;
    private final CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");
    private Comment testComment = Comment.of(createCommentRequestDto);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Post mockPost = mock(Post.class);
        when(postService.getPostByPostId(anyLong())).thenReturn(mockPost);
    }

    @Test
    @DisplayName("createdComment")
    void createComment() {
        //given
        when(commentService.createComment(createCommentRequestDto)).thenReturn(testComment);

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
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testComment));

        //when
        Comment testComment = commentService.getCommentByCommentId(1L);

        //then
        String expectedCommentContent = createCommentRequestDto.getContent();
        String actualCommentContent = testComment.getContent();

        assertNotNull(testComment, "The actual comment should not be null");
        assertEquals(expectedCommentContent, actualCommentContent, "Expected Comment Content: " + expectedCommentContent + ", Actual Comment Content: " + actualCommentContent);
    }
}