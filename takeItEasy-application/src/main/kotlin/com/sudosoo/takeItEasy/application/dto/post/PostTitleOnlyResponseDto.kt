package com.sudosoo.takeItEasy.application.dto.post

import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.entity.Post

class PostTitleOnlyResponseDto(
    val postId: Long,
    val title: String,
    val likeCount: Int,
    val viewCount: Int,
    val writerName: String
) {
    constructor(post: EsPost):this(
        postId = post.id,
        title = post.title,
        likeCount = post.likeCount,
        viewCount = post.viewCount,
        writerName = post.writer
    )

    constructor(post: Post):this(
        postId = post.id,
        title = post.title,
        likeCount = post.hearts.size,
        viewCount = post.viewCount,
        writerName = post.writer
    )

}
