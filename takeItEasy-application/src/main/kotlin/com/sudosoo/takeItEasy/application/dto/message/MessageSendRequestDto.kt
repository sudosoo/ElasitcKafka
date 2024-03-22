package com.sudosoo.takeItEasy.application.dto.message


class MessageSendRequestDto(
    val memberId :Long ,
    val targetMemberId: Long ,
    val content : String ,
    val messageType : String)

