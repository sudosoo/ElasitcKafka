package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.service.CommentService;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommnetController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public void createComment(
            @RequestParam("memberId") Long memberId,
            @RequestParam("postId") Long postId,
            @RequestParam("content") String content
    ) {
        CreateCommentRequestDto requestDto = new CreateCommentRequestDto( memberId, postId, content);
        commentService.createComment(requestDto);
    }


}