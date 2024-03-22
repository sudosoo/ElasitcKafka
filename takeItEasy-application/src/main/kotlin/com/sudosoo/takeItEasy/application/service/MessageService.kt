package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.message.MessageSendRequestDto

interface MessageService {
    fun send(requestDto: MessageSendRequestDto)
}
