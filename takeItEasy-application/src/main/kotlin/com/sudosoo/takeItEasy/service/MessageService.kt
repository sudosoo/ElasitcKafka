package com.sudosoo.takeItEasy.service

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto

interface MessageService {
    fun send(requestDto: MessageSendRequestDto)
}
