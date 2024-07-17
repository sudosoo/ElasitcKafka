package com.sudosoo.takeItEasy.application.service.heart

import com.sudosoo.takeItEasy.application.dto.heart.CommentHeartRequestDto
import com.sudosoo.takeItEasy.application.dto.heart.PostHeartRequestDto
import com.sudosoo.takeItEasy.domain.entity.Heart


interface HeartService {
    fun createPostHeart(heartRequestDTO: PostHeartRequestDto): Heart
    fun createCommentHeart(heartRequestDTO: CommentHeartRequestDto): Heart
    fun postDisHeart(heartRequestDTO: PostHeartRequestDto)
    fun commentDisHeart(heartRequestDTO: CommentHeartRequestDto)
}
