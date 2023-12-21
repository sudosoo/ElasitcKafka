package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.post.PostDetailResponsetDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping(value = "/createPost" , name = "createPost")
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostRequestDto requestDto) {

        postService.createdPost(requestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getPostDetail" , name = "getPostDetail")
    public ResponseEntity<Page<PostDetailResponsetDto>> getPostDetail
            (@RequestParam Long postId,
             @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        PageRequest pageRequest = PageRequest.of(pageNo,10);

        postService.getPostDetailByPostId(postId,pageRequest);

        return ResponseEntity.ok().build();
    }




}