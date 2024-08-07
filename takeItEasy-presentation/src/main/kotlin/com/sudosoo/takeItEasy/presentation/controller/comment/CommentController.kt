package com.sudosoo.takeItEasy.presentation.controller.comment

import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.application.service.comment.CommentService
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