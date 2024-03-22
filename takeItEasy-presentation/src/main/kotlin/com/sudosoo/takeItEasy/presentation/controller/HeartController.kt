package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.heart.CommentHeartRequestDto
import com.sudosoo.takeItEasy.application.dto.heart.PostHeartRequestDto
import com.sudosoo.takeItEasy.application.service.HeartService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/heart")
class HeartController (val heartService: HeartService){

    @PostMapping("/createPostHeart", name = "createPostHeart")
    fun createPostHeart(@RequestBody @Valid requestDto: PostHeartRequestDto): ResponseEntity<Void> {
        heartService.createPostHeart(requestDto)

        return ResponseEntity.ok().build<Void>()
    }

    @PostMapping("/createCommentHeart", name = "createCommentHeart")
    fun createCommentHeart(@RequestBody @Valid requestDto: CommentHeartRequestDto): ResponseEntity<Void> {
        heartService.createCommentHeart(requestDto)

        return ResponseEntity.ok().build<Void>()
    }

    @PutMapping("/createPostDisHeart", name = "createPostDisHeart")
    fun createPostDisHeart(@RequestBody @Valid requestDto: PostHeartRequestDto): ResponseEntity<Void> {
        heartService.postDisHeart(requestDto)

        return ResponseEntity.ok().build<Void>()
    }

    @PutMapping("/createCommentDisHeart", name = "createCommentDisHeart")
    fun createCommentDisHeart(@RequestBody @Valid requestDto: CommentHeartRequestDto): ResponseEntity<Void> {
        heartService.commentDisHeart(requestDto)

        return ResponseEntity.ok().build<Void>()
    }
}