package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping(value = "/create" , name = "createComment")
    public ResponseEntity<Void> createComment(@Valid @RequestBody CreateCommentRequestDto requestDto) {
        commentService.create(requestDto);

        return ResponseEntity.ok().build();
    }
}