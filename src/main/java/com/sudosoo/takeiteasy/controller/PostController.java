package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.common.CustomNotify;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping(value = "/create" , name = "createPost")
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostRequestDto requestDto) {

        postService.createdPost(requestDto);

        return ResponseEntity.ok().build();
    }

    @CustomNotify
    @GetMapping(value = "/getDetail" , name = "getPostDetail")
    public ResponseEntity<PostDetailResponseDto> getPostDetail
            (@RequestParam Long postId,
             @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        Pageable pageRequest = PageRequest.of(pageNo,10);

        return new ResponseEntity<>(postService.getPostDetailByPostId(postId,pageRequest), HttpStatus.OK);
    }

}