package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final KafkaProducer kafkaProducer;

    @PostMapping(value = "/message" , name = "카프카 테스트 API")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return "success";
    }
}