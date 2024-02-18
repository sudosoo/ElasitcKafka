package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping(value = "/createPostHeart" , name = "createPostHeart")
    public ResponseEntity<Void> createPostHeart(@Valid @RequestBody PostHeartRequestDto requestDto) {

        heartService.createPostHeart(requestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/createCommentHeart" , name = "createCommentHeart")
    public ResponseEntity<Void> createCommentHeart(@Valid @RequestBody CommentHeartRequestDto requestDto) {

        heartService.createCommentHeart(requestDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/createPostDisHeart" , name = "createPostDisHeart")
    public ResponseEntity<Void> createPostDisHeart(@Valid @RequestBody PostHeartRequestDto requestDto) {

        heartService.postDisHeart(requestDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/createCommentDisHeart" , name = "createCommentDisHeart")
    public ResponseEntity<Void> createCommentDisHeart(@Valid @RequestBody CommentHeartRequestDto requestDto) {

        heartService.commentDisHeart(requestDto);

        return ResponseEntity.ok().build();
    }
}