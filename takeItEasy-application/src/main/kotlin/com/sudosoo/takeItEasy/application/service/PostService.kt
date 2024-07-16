package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

interface PostService {

    @Throws(ExecutionException::class, InterruptedException::class, IOException::class, TimeoutException::class)
    fun create(createPostRequestDto: CreatePostRequestDto): TestPostResponseDto
    fun defaultCreate(requestDto: CreatePostRequestDto)
    fun getByPostId(postId: Long): Post
    fun getPostDetailByPostId(postId: Long, pageRequest: Pageable): PostDetailResponseDto
    fun createBatchPosts(count: Int): Post
    fun softDeletePost(postId: Long)
    fun getPaginationPost(pageRequest: Pageable) : Page<PostTitleOnlyResponseDto>
    fun postRepositoryRedisSynchronization()

}
