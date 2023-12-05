package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Post;
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
public class PostController {
    private final PostService postService;
    @PostMapping("/post")
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostRequestDto requestDto) {
        Post post = postService.createdPost(requestDto);
        return ResponseEntity.ok().build();
    }

}