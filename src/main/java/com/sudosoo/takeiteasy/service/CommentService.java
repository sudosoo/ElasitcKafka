package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;

public interface CommentService {
    void createComment(CreateCommentRequestDto createCommentRequestDto);
    Comment getCommentByCommentId(Long commentId);
}
