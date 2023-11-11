package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.HeartRequestDto;
import com.sudosoo.takeiteasy.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/postHeart")
    public void PostHeart(
            @RequestParam("memberId") Long memberId,
            @RequestParam("postId") Long postId
    ) {
        HeartRequestDto requestDto = HeartRequestDto.builder()
                                        .postId(postId)
                                        .memberId(memberId)
                                        .build();
        heartService.creatPostHeart(requestDto);
    }

    @PostMapping("/commentHeart")
    public void CommentHeart(
            @RequestParam("memberId") Long memberId,
            @RequestParam("commentId") Long commentId
    ) {
        HeartRequestDto requestDto = HeartRequestDto.builder()
                                        .commentId(commentId)
                                        .memberId(memberId)
                                        .build();
        heartService.creatCommentHeart(requestDto);
    }

    @PutMapping("/postDisHeart")
    public void PostDisHeart(
            @RequestParam("memberId") Long memberId,
            @RequestParam("postId") Long postId
    ) {
        HeartRequestDto requestDto = HeartRequestDto.builder()
                                        .postId(postId)
                                        .memberId(memberId)
                                        .build();
        heartService.postDisHeart(requestDto);
    }

    @PutMapping("/commentDisHeart")
    public void CommentDisHeart(
            @RequestParam("memberId") Long memberId,
            @RequestParam("commentId") Long commentId
    ) {
        HeartRequestDto requestDto = HeartRequestDto.builder()
                                        .commentId(commentId)
                                        .memberId(memberId)
                                        .build();
        heartService.commentDisHeart(requestDto);
    }

}