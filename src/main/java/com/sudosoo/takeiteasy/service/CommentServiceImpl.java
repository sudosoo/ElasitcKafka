package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public void createComment(CreateCommentRequestDto createCommentRequestDto){
        Member member = memberService.getMemberByMemberId(createCommentRequestDto.getMemberId());
        Post post = postService.getPostByPostId(createCommentRequestDto.getPostId());
        Comment comment = Comment.buildEntityFromDto(post,member, createCommentRequestDto.getContent());
        commentRepository.save(comment);
    }

    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found comment id : " + commentId));
    }
}
