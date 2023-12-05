package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.common.AutoLogging;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private static final Logger kafkaLogger = LoggerFactory.getLogger("kafkaLogger");
    private final KafkaProducer kafkaProducer;
    private final MemberService memberService;


    @AutoLogging
    @GetMapping("/hello")
    public void hello(@RequestParam(value = "name") String name) {
        kafkaLogger.info("name:{}", name);
    }

    @AutoLogging
    @PostMapping( "/message")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return "success";
    }
}