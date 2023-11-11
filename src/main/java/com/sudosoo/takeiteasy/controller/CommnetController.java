package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.service.CommentService;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommnetController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public ResponseEntity<Void> createComment(@Valid @RequestBody CreateCommentRequestDto requestDto) {
        commentService.createComment(requestDto);
        return ResponseEntity.ok().build();
    }


}