package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.batch.BatchLauncherService;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final BatchLauncherService batchLauncherService;


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
}