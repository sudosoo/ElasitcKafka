package com.sudosoo.takeiteasy.application.service;

import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeItEasy.application.service.CommentServiceImpl;
import com.sudosoo.takeItEasy.application.service.PostService;
import com.sudosoo.takeItEasy.domain.entity.Comment;
import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostService postService;
    @InjectMocks
    private CommentServiceImpl commentService;
    private final CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");
    private Comment testComment = Comment.of(createCommentRequestDto.getContent());
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Post mockPost = mock(Post.class);
        when(postService.getByPostId(anyLong())).thenReturn(mockPost);
    }

    @Test
    @DisplayName("createdComment")
    void createComment() {
        //given
        when(commentRepository.save(any())).thenReturn(testComment);

        //when
        Comment resultComment = commentService.create(createCommentRequestDto);

        //then
        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    void getCommentByCommentId() {
        //given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testComment));

        //when
        Comment resultComment = commentService.getByCommentId(1L);

        //then
        String expectedCommentContent = createCommentRequestDto.getContent();
        String actualCommentContent = resultComment.getContent();

        verify(commentRepository,times(1)).findById(any());
        assertEquals(expectedCommentContent, actualCommentContent, "Expected Comment Content: " + expectedCommentContent + ", Actual Comment Content: " + actualCommentContent);
    }
}