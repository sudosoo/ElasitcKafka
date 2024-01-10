package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.service.MessageService;
import jakarta.validation.Valid;
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

        messageService.messageSend(requestDto);

        return ResponseEntity.ok().build();
    }


}