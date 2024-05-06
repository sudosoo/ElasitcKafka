package com.sudosoo.takeItEasy.application.dto.post

import com.sudosoo.takeItEasy.domain.entity.Post

class PostTitleOnlyResponseDto(
    var postId: Long,
    var title: String,
    var likeCount: Int,
    var viewCount: Int,
    var writerName: String
) {
    constructor(post: Post):this(
        postId = post.id,
        title = post.title,
        likeCount = post.hearts.size,
        viewCount = post.viewCount,
        writerName = post.writer
    )


}
