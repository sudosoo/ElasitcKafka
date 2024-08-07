package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.application.dto.post.PostCQRSDto
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.application.service.post.PostService
import com.sudosoo.takeItEasy.batch.schedule.Scheduler
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController (
    val postService: PostService,
){
    @PostMapping("/synchronize", name = "repositoryRedisSynchronization")
    fun repositoryRedisSynchronization() {
        postService.postRepositoryRedisSynchronization()
    }

    @PostMapping("/createPost", name = "createPost")
    fun createPost(@RequestBody requestDto: CreatePostRequestDto) {
        postService.defaultCreate(requestDto)
    }

}