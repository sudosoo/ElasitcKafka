package com.sudosoo.takeiteasy.dto.notice;

import com.sudosoo.takeiteasy.entity.Notice;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {
    @NotNull(message = "내용을 입력해 주세요.")
    private String content;
    private boolean read = false;
    public NoticeResponseDto(Notice notice) {
        this.content = notice.getContent();
    }
}
