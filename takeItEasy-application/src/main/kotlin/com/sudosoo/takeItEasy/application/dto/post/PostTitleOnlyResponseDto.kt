package com.sudosoo.takeItEasy.application.dto.post

class PostTitleOnlyResponseDto(
    val postId: Long,
    val title: String,
    val likeCount: Int,
    val viewCount: Int,
    val writerName: String
)
