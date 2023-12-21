package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostListResponsetDto;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

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

    @GetMapping(value = "/getPost" , name = "getPost")
    public ResponseEntity<Page<PostListResponsetDto>> getPosts
            (@RequestParam Long categoryId,
             @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        PageRequest pageRequest = PageRequest.of(pageNo,10);
        postService.getPostByCategoryId(categoryId,pageRequest);
        return ResponseEntity.ok().build();
    }


}