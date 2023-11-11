package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.PostHeartRequestDto;
import com.sudosoo.takeiteasy.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/postHeart")
    public ResponseEntity<Void> PostHeart(@Valid @RequestBody PostHeartRequestDto requestDto) {
        heartService.creatPostHeart(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/commentHeart")
    public ResponseEntity<Void> CommentHeart(@Valid @RequestBody CommentHeartRequestDto requestDto) {
        heartService.creatCommentHeart(requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/postDisHeart")
    public ResponseEntity<Void> PostDisHeart(@Valid @RequestBody PostHeartRequestDto requestDto) {
        heartService.postDisHeart(requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/commentDisHeart")
    public ResponseEntity<Void> CommentDisHeart(@Valid @RequestBody CommentHeartRequestDto requestDto) {
        heartService.commentDisHeart(requestDto);
        return ResponseEntity.ok().build();
    }
}