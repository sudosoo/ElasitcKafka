package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;

public interface MessageService {

    void send(MessageSendRequestDto requestDto);
}