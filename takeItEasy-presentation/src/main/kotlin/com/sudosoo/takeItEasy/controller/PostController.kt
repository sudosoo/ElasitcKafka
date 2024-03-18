package com.sudosoo.takeItEasy.controller

import com.sudosoo.takeItEasy.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.dto.post.PostDetailResponseDto
import com.sudosoo.takeItEasy.dto.post.TestPostResponseDto
import com.sudosoo.takeItEasy.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.service.PostService
import com.sudosoo.takeiteasy.common.annotation.CustomNotify
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

@RestController
@RequestMapping("/api/post")
class PostController(val postService: PostService) {
    @PostMapping("/create", name = "createPost")
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class, TimeoutException::class)
    fun createPost(@RequestBody @Valid requestDto:CreatePostRequestDto): ResponseEntity<TestPostResponseDto> {
        val result: TestPostResponseDto = postService.create(requestDto)
        return ResponseEntity.ok(result)
    }

    @CustomNotify
    @GetMapping("/getDetail", name = "getPostDetail")
    fun getPostDetail(
        @RequestParam postId: Long,
        @RequestParam(required = false, defaultValue = "0", value = "page") pageNo: Int
    ): ResponseEntity<PostDetailResponseDto> {
        var pageNo = pageNo
        pageNo = if ((pageNo == 0)) 0 else (pageNo - 1)
        val pageRequest: Pageable = PageRequest.of(pageNo, 10)

        return ResponseEntity(postService.getPostDetailByPostId(postId, pageRequest), HttpStatus.OK)
    }

    @GetMapping("/get", name = "getPaginationPost")
    fun getPaginationPost(
        @RequestParam(
            required = false,
            defaultValue = "0",
            value = "page"
        ) pageNo: Int
    ): ResponseEntity<List<PostTitleOnlyResponseDto>> {
        var pageNo = pageNo
        pageNo = if ((pageNo == 0)) 0 else (pageNo - 1)
        val pageRequest: Pageable = PageRequest.of(pageNo, 10)

        return ResponseEntity(postService.getPaginationPost(pageRequest), HttpStatus.OK)
    }

    @GetMapping("/getAll", name = "getAll")
    fun allPost(): List<Any>{
        return postService.allPost
    }
}