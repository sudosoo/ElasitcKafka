package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment createComment(CreateCommentRequestDto createCommentRequestDto);
    Comment getCommentByCommentId(Long commentId);
    Page<Comment> getCommentPaginationByPostId(Long postId, Pageable pageRequest);
}
