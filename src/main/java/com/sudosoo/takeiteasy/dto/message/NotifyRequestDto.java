package com.sudosoo.takeiteasy.dto.message;

import com.sudosoo.takeiteasy.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotifyRequestDto {

    private final Long memberId = 1L;
    @NotNull(message = "내용을 입력해 주세요.")
    private String content;
    private final MessageType messageType = MessageType.NOTIFY ;
    public NotifyRequestDto(String content) {
        this.content = content;
    }
}
