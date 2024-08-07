package com.sudosoo.takeItEasy.presentation.controller.notice

import com.sudosoo.takeItEasy.application.emitter.EmitterProducer
import com.sudosoo.takeItEasy.application.service.notice.NoticeService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/notice")
class NoticeController(
    val emitterProducer: EmitterProducer
) {

    @GetMapping("/subscribe", produces = ["text/event-stream"])
    fun subscribe(
        @RequestParam memberName : String,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") lastEventId : String
    ): SseEmitter {
        return emitterProducer.subscribe(memberName, lastEventId)
    }
}