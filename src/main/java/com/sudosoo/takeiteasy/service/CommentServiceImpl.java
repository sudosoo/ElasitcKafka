package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public Comment createComment(CreateCommentRequestDto createCommentRequestDto){
        Member member = memberService.getMemberByMemberId(createCommentRequestDto.getMemberId());
        Post post = postService.getPostByPostId(createCommentRequestDto.getPostId());
        Comment comment = Comment.of(createCommentRequestDto);

        comment.setPost(post);
        comment.setMember(member);

        return commentRepository.save(comment);
    }

    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found comment id : " + commentId));
    }
}
