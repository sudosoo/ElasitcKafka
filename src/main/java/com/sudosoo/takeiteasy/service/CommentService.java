package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment create(CreateCommentRequestDto createCommentRequestDto);
    Comment getByCommentId(Long commentId);
    Page<Comment> getCommentPaginationByPostId(Long postId, Pageable pageRequest);
}
