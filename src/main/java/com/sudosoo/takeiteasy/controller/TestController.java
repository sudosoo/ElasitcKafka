package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.batch.BatchLauncherService;
import com.sudosoo.takeiteasy.dto.post.PostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostResponseDto;
import com.sudosoo.takeiteasy.redis.RedisService;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final BatchLauncherService batchLauncherService;
    private final RedisService redisService;
    private final PostService postService;


    @PostMapping(value = "/batchCreateDummyPost" , name = "batchCreateDummyPost")
    public ResponseEntity<Void> batchCreateDummyPost() {
        //batch를 로직 통한 실행
        batchLauncherService.runBatchJob();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/defaultCreateDummyPost" , name = "createDummyPost")
    public ResponseEntity<Void> defaultCreateDummyPost() throws Exception {
        //기존 로직
        batchLauncherService.runBatchJobV2();

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/redis" , name = "redisTest")
    public void redisTest()  {
        redisService.addToRedis();
    }

    @GetMapping(value = "/getV" , name = "getV")
    public List<PostResponseDto> redisGetTest()  {
        return redisService.getV("PostResponseDto");
    }
    @PostMapping(value = "/createPost" , name = "createPost")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto)  {
        return postService.redisTest(requestDto);
    }

}