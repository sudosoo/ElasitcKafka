package com.sudosoo.takeItEasy.dto.comment

class CommentResponseDto(
    val commentId: Long,
    val writerName: String,
    val content: String,
    val likeCount: Int = 0
)
