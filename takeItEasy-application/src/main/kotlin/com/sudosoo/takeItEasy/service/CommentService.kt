package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeiteasy.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun create(createCommentRequestDto: CreateCommentRequestDto): Comment
    fun getByCommentId(commentId: Long): Comment
    fun getCommentPaginationByPostId(postId: Long, pageRequest: Pageable): Page<Comment>
}
