package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberService memberService;

    @Override
    public Comment createComment(CreateCommentRequestDto createCommentRequestDto){
        Member member = memberService.getMemberByMemberId(createCommentRequestDto.getMemberId());
        Post post = postRepository.findById(createCommentRequestDto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("Could not found comment id : "+createCommentRequestDto.getPostId() ));
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

    @Override
    public Page<Comment> getCommentPaginationByPostId(Long postId, Pageable pageRequest) {
        return commentRepository.findCommentsByPostId(postId, pageRequest);
    }
}
