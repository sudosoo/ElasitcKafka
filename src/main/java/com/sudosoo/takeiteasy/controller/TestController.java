package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.kafka.KafkaNoticeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final KafkaNoticeProducer kafkaNoticeProducer;

    @PostMapping(value = "/message" , name = "카프카 테스트 API")
    public String sendMessage(@RequestParam("message") String message) {

        kafkaNoticeProducer.sendTest(message);

        return "success";
    }
}