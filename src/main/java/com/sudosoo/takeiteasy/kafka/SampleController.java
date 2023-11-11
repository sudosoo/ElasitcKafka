package com.sudosoo.takeiteasy.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/kafka/test")
@RequiredArgsConstructor
public class SampleController {
    private final KafkaProducer producer;


    @PostMapping(value = "/message")
    public String sendMessage(@RequestParam("message") String message) {
        this.producer.sendMessage(message);
        return "success";
    }


    @PutMapping(value = "/message")
    public void sendMessage() {
        throw new IllegalArgumentException("에러테스트");
    }
}