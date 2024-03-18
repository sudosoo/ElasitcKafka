package com.sudosoo.takeItEasy.controller;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @PostMapping(value = "/send" , name = "messageSend")
    public ResponseEntity<Void> messageSend(@RequestBody MessageSendRequestDto requestDto) {

        messageService.send(requestDto);

        return ResponseEntity.ok().build();
    }


}