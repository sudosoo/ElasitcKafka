package com.sudosoo.takeiteasy.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto {

    private Long commentId;
    private String writerName;
    private String content;
    private int likeCount = 0;

    public CommentResponseDto(Long commentId, String writerName, String content, int likeCount) {
        this.commentId = commentId;
        this.writerName = writerName;
        this.content = content;
        this.likeCount = likeCount;
    }

}
