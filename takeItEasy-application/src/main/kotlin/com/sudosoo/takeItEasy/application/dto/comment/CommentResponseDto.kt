package com.sudosoo.takeItEasy.application.dto.comment

import com.sudosoo.takeItEasy.domain.entity.Comment

class CommentResponseDto(
    val commentId: Long,
    val writerName: String,
    val content: String,
    val likeCount: Int = 0
){
    fun toResponseDto (comment: Comment, heart : List<CommentHeart>) : CommentResponseDto{
        return CommentResponseDto(
            commentId = comment.id,
            writerName = comment.writerName,
            content = comment.content,
            likeCount = heart.size)
    }
}
