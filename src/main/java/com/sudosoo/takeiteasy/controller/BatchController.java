package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.batch.BatchLauncherService;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {
    private final BatchLauncherService batchLauncherService;
    private final PostService postService;

    @PostMapping(value = "/batchCreateDummyPost" , name = "batchCreateDummyPost")
    public ResponseEntity<Void> batchCreateDummyPost() {

        //batch를 로직 통한 실행
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            batchLauncherService.runBatchJob();
        } finally {
            stopWatch.stop();
            System.out.println("Total time to create 100,000 posts: " + stopWatch.getTotalTimeMillis() + "ms");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/defaultCreateDummyPost" , name = "createDummyPost")
    public ResponseEntity<Void> defaultCreateDummyPost() {

        //기존 로직
        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        try {
            postService.createDefaultPosts(100000);

        } finally {
            stopWatch2.stop();
            System.out.println("Total time to create 100,000 posts : " + stopWatch2.getTotalTimeMillis() + "ms");
        };

        return ResponseEntity.ok().build();
    }
}
