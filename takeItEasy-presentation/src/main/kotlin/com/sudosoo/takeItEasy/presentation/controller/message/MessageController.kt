package com.sudosoo.takeItEasy.presentation.controller.message

import com.sudosoo.takeItEasy.application.dto.message.MessageSendRequestDto
import com.sudosoo.takeItEasy.application.service.message.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/message")
class MessageController(val messageService: MessageService) {
    @PostMapping("/send", name = "messageSend")
    fun messageSend(@RequestBody requestDto: MessageSendRequestDto): ResponseEntity<Void> {
        messageService.send(requestDto)

        return ResponseEntity.ok().build<Void>()
    }
}