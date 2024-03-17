package com.sudosoo.takeiteasy.dto.notice;

import com.sudosoo.takeiteasy.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeRequestDto {

    @NotNull(message = "내용을 입력해 주세요.")
    private String content;

    public NoticeRequestDto(String content) {
        this.content = content;
    }
}
