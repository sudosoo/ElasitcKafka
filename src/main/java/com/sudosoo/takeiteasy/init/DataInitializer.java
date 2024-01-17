package com.sudosoo.takeiteasy.init;

import com.sudosoo.takeiteasy.service.BatchLauncherService;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Transactional
public class DataInitializer implements CommandLineRunner {

    private final PostService postService;

    private final BatchLauncherService batchLauncherService;

    @Override
    public void run(String... args) throws Exception {

        //batch를 로직 통한 실행
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            batchLauncherService.runBatchJob();
        } finally {
            stopWatch.stop();
            System.out.println("Total time to create 100,000 posts: " + stopWatch.getTotalTimeMillis() + "ms");
        }

//        //기존 로직
//        StopWatch stopWatch2 = new StopWatch();
//        stopWatch2.start();
//        try {
//            postService.createDefaultPosts(100000);
//
//        } finally {
//            stopWatch2.stop();
//            System.out.println("Total time to create 100,000 posts : " + stopWatch2.getTotalTimeMillis() + "ms");
//        }
    }
}