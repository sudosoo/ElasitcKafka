package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/post")
    public void createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("memberId") Long memberId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "relatedPostId", required = false) Long relatedPostId
    ) {
        CreatePostRequestDto requestDto = new CreatePostRequestDto(title, content, memberId, categoryId, relatedPostId);
        postService.creatPost(requestDto);
    }

}