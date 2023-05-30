package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.entity.Comment;

public interface CommentService {
    Comment getCommentByCommentId(Long commentId);
}
