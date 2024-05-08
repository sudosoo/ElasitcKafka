package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

interface PostService {

    @Throws(ExecutionException::class, InterruptedException::class, IOException::class, TimeoutException::class)
    fun create(createPostRequestDto: CreatePostRequestDto): TestPostResponseDto
    fun getByPostId(postId: Long): Post
    fun getPostDetailByPostId(postId: Long, pageRequest: Pageable): PostDetailResponseDto
    fun createBatchPosts(count: Int): Post
    fun redisTest(requestDto: CreatePostRequestDto): TestPostResponseDto
    fun allPost() : List<TestPostResponseDto>
    fun softDeletePost(postId: Long): Post
}
