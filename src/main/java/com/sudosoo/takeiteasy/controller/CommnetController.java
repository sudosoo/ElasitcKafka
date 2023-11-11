package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.service.CommentService;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommnetController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public ResponseEntity<Void> createComment(@Valid @RequestBody CreateCommentRequestDto requestDto) {
        Comment comment = commentService.createComment(requestDto);
        log.info("New Comment created :  memberName{} , CommentId {}",comment.getUserName(), comment.getId());
        return ResponseEntity.ok().build();
    }


}