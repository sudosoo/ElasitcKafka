package com.sudosoo.takeItEasy.dto.message

import com.sudosoo.takeiteasy.entity.MessageType

class MessageSendRequestDto(
    val memberId :Long ,
    val targetMemberId: Long ,
    val content : String ,
    val messageType : MessageType = MessageType.MESSAGE)

