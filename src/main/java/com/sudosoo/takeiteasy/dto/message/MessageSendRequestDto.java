package com.sudosoo.takeiteasy.dto.message;

import com.sudosoo.takeiteasy.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageSendRequestDto {

    private Long memberId;
    @NotNull(message = "상대방 이름을 작성해 주세요.")
    private String targetMemberName;
    @NotNull(message = "내용을 입력해 주세요.")
    private String content;
    @NotNull
    private MessageType messageType = MessageType.MESSAGE ;
}
