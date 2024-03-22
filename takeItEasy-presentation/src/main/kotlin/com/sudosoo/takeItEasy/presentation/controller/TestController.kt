package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.post.PostRequestDto
import com.sudosoo.takeItEasy.application.dto.post.TestPostResponseDto
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.application.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController (
//    val batchLauncherService: BatchLauncherService,
    val redisService: RedisService,
    val postService: PostService)
{
//    @PostMapping("/batchCreateDummyPost", name = "batchCreateDummyPost")
//    fun batchCreateDummyPost(): ResponseEntity<Void> {
//        //batch를 로직 통한 실행
//        batchLauncherService.runBatchJob()
//        return ResponseEntity.ok().build()
//    }
//
//    @PostMapping("/defaultCreateDummyPost", name = "createDummyPost")
//    @Throws(Exception::class)
//    fun defaultCreateDummyPost(): ResponseEntity<Void> {
//        //기존 로직
//        batchLauncherService.runBatchJobV2()
//
//        return ResponseEntity.ok().build<Void>()
//    }

    @GetMapping("/getV", name = "getV")
    fun redisGetTest(): List<TestPostResponseDto> {
        val methodName = "PostResponseDto"
        return redisService.getValues(methodName)
    }


    @PostMapping("/createPost", name = "createPost")
    fun createPost(@RequestBody requestDto: PostRequestDto): TestPostResponseDto {
        return postService.redisTest(requestDto)
    }

    @PostMapping("/synchronize", name = "repositoryRedisSynchronization")
    fun repositoryRedisSynchronization() {
        redisService.postRepositoryRedisSynchronization()
    }
}