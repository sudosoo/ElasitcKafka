package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

interface PostElasticService {
    fun create(createPostRequestDto: CreatePostRequestDto)
    fun findByTitle(title: String, pageRequest: PageRequest): List<Post>
    fun findByContent(content: String, pageRequest: PageRequest): List<Post>

}
