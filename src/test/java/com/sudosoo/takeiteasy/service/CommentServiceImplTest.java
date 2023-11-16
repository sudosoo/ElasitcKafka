package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(1L,1L,"TestContent");
    CommentRepository commentRepository = mock(CommentRepository.class);
    PostService postService = mock(PostService.class);
    MemberService memberService = mock(MemberService.class);
    CommentService commentService = new CommentServiceImpl(commentRepository,postService,memberService);

    @BeforeEach
    void setUp() {
        CreateMemberRequestDto mockMember = new CreateMemberRequestDto("TestMember");
        when(memberService.getMemberByMemberId(createCommentRequestDto.getMemberId())).thenReturn(Member.getInstance(mockMember));

        CreatePostRequestDto mockPost = new CreatePostRequestDto("TestTitle","TestContent",1L,null);
        when(postService.getPostByPostId(createCommentRequestDto.getPostId())).thenReturn(Post.getInstance(mockPost));
    }

    @Test
    @DisplayName("createdComment")
    void createComment() {
        //given
        when(commentService.createComment(createCommentRequestDto)).thenReturn(Comment.of(createCommentRequestDto));
        when(commentRepository.save(any(Comment.class))).thenReturn(Comment.of(createCommentRequestDto));

        //when
        Member member = memberService.getMemberByMemberId(createCommentRequestDto.getMemberId());
        Post post = postService.getPostByPostId(createCommentRequestDto.getPostId());
        Comment comment = commentService.createComment(createCommentRequestDto);

        comment.setMember(member);
        comment.setPost(post);

        //then
        String expectedContent = createCommentRequestDto.getContent();
        String actualContent = comment.getContent();

        assertNotNull(comment,"The created comment should not be null");
        assertEquals(expectedContent, actualContent, "Expected Content: " + expectedContent + ", Actual Content: " + actualContent);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getCommentByCommentId() {
        // given
        when(commentRepository.findById(any())).thenReturn(Optional.of(Comment.of(createCommentRequestDto)));

        // when
        Comment comment = commentService.getCommentByCommentId(1L);

        // then
        String expectedCommentContent = createCommentRequestDto.getContent();
        String actualCommentContent = comment.getContent();

        assertNotNull(comment, "The actual comment should not be null");
        assertEquals(expectedCommentContent, actualCommentContent, "Expected Comment Content: " + expectedCommentContent + ", Actual Comment Content: " + actualCommentContent);
    }
}