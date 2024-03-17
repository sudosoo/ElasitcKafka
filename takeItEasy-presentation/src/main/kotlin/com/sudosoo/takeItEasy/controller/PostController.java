package com.sudosoo.takeItEasy.controller;

import com.sudosoo.takeiteasy.common.annotation.CustomNotify;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping(value = "/create" , name = "createPost")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto requestDto) throws ExecutionException, InterruptedException, IOException, TimeoutException {

        PostResponseDto result = postService.create(requestDto);

        return ResponseEntity.ok(result);
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

    @GetMapping(value = "/get" , name = "getPaginationPost")
    public ResponseEntity<List<PostTitleOnlyResponseDto>> getPaginationPost(@RequestParam(required = false, defaultValue = "0", value = "page")int pageNo ) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        Pageable pageRequest = PageRequest.of(pageNo,10);

        return new ResponseEntity<>(postService.getPaginationPost(pageRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll" , name = "getAll")
    public List<PostResponseDto> getAllPost() {
        return postService.getAllPost();
    }
}