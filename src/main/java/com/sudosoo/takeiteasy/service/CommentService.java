package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;

public interface CommentService {
    Comment createComment(CreateCommentRequestDto createCommentRequestDto);
    Comment getCommentByCommentId(Long commentId);
}
