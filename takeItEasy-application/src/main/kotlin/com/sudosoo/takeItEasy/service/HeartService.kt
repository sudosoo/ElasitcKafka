package com.sudosoo.takeItEasy.service

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto

interface HeartService {
    fun createPostHeart(heartRequestDTO: PostHeartRequestDto): Heart
    fun createCommentHeart(heartRequestDTO: CommentHeartRequestDto): Heart
    fun postDisHeart(heartRequestDTO: PostHeartRequestDto)
    fun commentDisHeart(heartRequestDTO: CommentHeartRequestDto)
}
