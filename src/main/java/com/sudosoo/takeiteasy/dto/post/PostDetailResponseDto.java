package com.sudosoo.takeiteasy.dto.post;

import com.sudosoo.takeiteasy.aspect.notice.NotifyInfo;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PostDetailResponseDto implements NotifyInfo {

    private Long postId;
    private String title;
    private String content;
    private String writerName;
    private Page<CommentResponseDto> commentsResponseDto;

    public PostDetailResponseDto(Long postId, String title, String content,
                                 String writerName, Page<CommentResponseDto> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.commentsResponseDto = comments;
    }

    @Override
    public String getReceiverName() {
        return this.writerName;
    }

    @Override
    public String getNotifyContent() {
        return this.content;
    }
}
