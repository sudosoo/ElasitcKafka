package com.sudosoo.takeItEasy.controller

import com.sudosoo.takeItEasy.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.service.CommentService
import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeiteasy.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comment")
class CommentController(val commentService : CommentService) {

    @PostMapping("/create", name = "createComment")
    fun createComment(@RequestBody @Valid  requestDto: CreateCommentRequestDto): ResponseEntity<Void> {
        commentService.create(requestDto)

        return ResponseEntity.ok().build<Void>()
    }
}