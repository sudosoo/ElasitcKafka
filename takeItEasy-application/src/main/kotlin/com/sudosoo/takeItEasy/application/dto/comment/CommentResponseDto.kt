package com.sudosoo.takeItEasy.application.dto.comment

import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Heart

class CommentResponseDto(
    val commentId: Long,
    val writerName: String,
    val content: String,
    val likeCount: Int
){
    constructor(comment: Comment) : this (
        commentId = comment.id,
        writerName = comment.writerName,
        content = comment.content,
        likeCount = comment.hearts.size
    )
}
