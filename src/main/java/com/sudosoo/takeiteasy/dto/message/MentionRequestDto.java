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
public class MentionRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private MessageType messageType = MessageType.MENTION ;
}
