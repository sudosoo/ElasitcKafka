package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
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
    private final PostService postService;

    @Override
    public Comment createComment(CreateCommentRequestDto createCommentRequestDto){
        //TODO MemberSetting
        Long memberId = createCommentRequestDto.getMemberId();
        Post post = postService.getPostByPostId(createCommentRequestDto.getPostId());
        Comment comment = Comment.of(createCommentRequestDto);

        comment.setPost(post);
        comment.setMember(memberId);

        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found comment id : " + commentId));
    }

    @Override
    public Page<Comment> getCommentPaginationByPostId(Long postId, Pageable pageRequest) {
        return commentRepository.findCommentsByPostId(postId,pageRequest);
    }
}
