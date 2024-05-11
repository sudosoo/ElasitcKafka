package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.annotation.CustomNotify
import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.application.dto.post.PostDetailResponseDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.application.dto.post.TestPostResponseDto
import com.sudosoo.takeItEasy.application.service.PostService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
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
    fun createPost(@RequestBody @Valid requestDto: CreatePostRequestDto): ResponseEntity<TestPostResponseDto> {
        val result: TestPostResponseDto = postService.create(requestDto)
        return ResponseEntity.ok(result)
    }

    @CustomNotify
    @GetMapping("/getDetail", name = "getPostDetail")
    fun getPostDetail(
        @RequestParam postId: Long,
        @RequestParam(required = false, defaultValue = "0", value = "page") pageNo: Int
    ): ResponseEntity<PostDetailResponseDto> {
        val currentPageNumber = if ((pageNo == 0)) 0 else (pageNo - 1)
        val pageRequest: Pageable = PageRequest.of(currentPageNumber, 10)

        return ResponseEntity(postService.getPostDetailByPostId(postId, pageRequest), HttpStatus.OK)
    }

    @GetMapping("/get", name = "getPaginationPost")
    fun getPaginationPost(
        @RequestParam(
            required = false,
            defaultValue = "0",
            value = "page"
        ) pageNum: Int
    ): ResponseEntity<Page<PostTitleOnlyResponseDto>> {
        val currentPageNumber = if ((pageNum == 0)) 0 else (pageNum - 1)
        val pageRequest:PageRequest = PageRequest.of(currentPageNumber, 10)

        return ResponseEntity(postService.getPaginationPost(pageRequest), HttpStatus.OK)
    }

    @PatchMapping("/delete", name = "softDeletePost")
    fun softDeletePost(@RequestParam postId: Long): ResponseEntity<Void> {
        postService.softDeletePost(postId)
        return ResponseEntity.ok().build()
    }
}