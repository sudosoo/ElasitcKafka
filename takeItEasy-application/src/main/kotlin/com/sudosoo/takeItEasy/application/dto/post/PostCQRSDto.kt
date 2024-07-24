package com.sudosoo.takeItEasy.application.dto.post

import com.sudosoo.takeItEasy.domain.entity.Post

class PostCQRSDto (val title: String, val writer: String){
    constructor(post : Post) : this(
        title = post.title,
        writer = post.writer
    )
}
