package com.sudosoo.takeItEasy.application.service.comment

import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.domain.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun create(createCommentRequestDto: CreateCommentRequestDto): Comment
    fun getByCommentId(commentId: Long): Comment
    fun getCommentPaginationByPostId(postId: Long, pageRequest: Pageable): Page<Comment>
}
