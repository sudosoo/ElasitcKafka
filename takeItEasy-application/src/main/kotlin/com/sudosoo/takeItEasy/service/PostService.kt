package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.post.*
import com.sudosoo.takeiteasy.entity.Post
import org.springframework.data.domain.Pageable
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

interface PostService {
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class, TimeoutException::class)
    fun create(createPostRequestDto: CreatePostRequestDto): TestPostResponseDto
    fun getByPostId(postId: Long): Post
    fun getPostDetailByPostId(postId: Long, pageRequest: Pageable): PostDetailResponseDto
    fun getPaginationPost(pageable: Pageable): List<PostTitleOnlyResponseDto>
    fun createBatchPosts(count: Int): Post
    fun redisTest(requestDto: PostRequestDto): TestPostResponseDto
    fun allPost() : List<TestPostResponseDto>
}
