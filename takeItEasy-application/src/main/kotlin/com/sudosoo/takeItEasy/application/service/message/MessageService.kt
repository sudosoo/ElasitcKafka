package com.sudosoo.takeItEasy.application.service.message

import com.sudosoo.takeItEasy.application.dto.message.MessageSendRequestDto

interface MessageService {
    fun send(requestDto: MessageSendRequestDto)
}
