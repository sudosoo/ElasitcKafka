package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateMemberRequestDto;
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
    @GetMapping("/hello")
    public void hello(@RequestParam(value = "name") String name) {
        kafkaLogger.info("name:{}", name);
    }

    @PostMapping( "/message")
    public String sendMessage(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(message);
        return "success";
    }

    @PostMapping( "/member")
    public void saveMember(@RequestParam("memberName")final CreateMemberRequestDto createMemberRequestDto) {
        memberService.createdMember(createMemberRequestDto);
    }

}