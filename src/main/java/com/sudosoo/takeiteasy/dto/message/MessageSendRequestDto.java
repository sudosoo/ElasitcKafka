package com.sudosoo.takeiteasy.dto.message;

import com.sudosoo.takeiteasy.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageSendRequestDto {

    private Long memberId;

    @NotNull(message = "상대방 이름을 작성해 주세요.")
    private Long targetMemberId;

    @NotNull(message = "내용을 입력해 주세요.")
    private String content;

    private final MessageType messageType = MessageType.MESSAGE ;

    public MessageSendRequestDto(Long memberId, Long targetMemberId, String content) {
        this.memberId = memberId;
        this.targetMemberId = targetMemberId;
        this.content = content;
    }
}
