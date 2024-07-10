package com.sudosoo.takeItEasy.application.dto.comment

import com.sudosoo.takeItEasy.domain.entity.Comment

class CommentResponseDto(
    val commentId: Long,
    val writer: String,
    val content: String,
    val likeCount: Int
){
    constructor(comment: Comment) : this (
        commentId = comment.id,
        writer = comment.writer,
        content = comment.content,
        likeCount = comment.hearts.size
    )
}
