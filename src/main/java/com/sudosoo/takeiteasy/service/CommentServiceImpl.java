package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    @Override
    public Comment createComment(CreateCommentRequestDto createCommentRequestDto){
        Member member = memberService.getMemberByMemberId(createCommentRequestDto.getMemberId());
        Post post = postService.getPostByPostId(createCommentRequestDto.getPostId());
        Comment comment = Comment.of(createCommentRequestDto);

        comment.setPost(post);
        comment.setMember(member);

        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found comment id : " + commentId));
    }
}
