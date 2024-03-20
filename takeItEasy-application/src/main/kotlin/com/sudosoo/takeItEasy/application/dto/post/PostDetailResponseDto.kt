package com.sudosoo.takeItEasy.application.dto.post

import com.sudosoo.takeItEasy.application.dto.comment.CommentResponseDto
import org.springframework.data.domain.Page

class PostDetailResponseDto(
    val postId: Long, val writerName: String,
    val title: String, val content: String, val comments: Page<CommentResponseDto>
)