package com.sudosoo.takeiteasy.dto.post;

import com.sudosoo.takeiteasy.aspect.notice.NotifyInfo;
import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.lang.reflect.Member;
import java.util.List;

@Getter
public class PostDetailResponsetDto implements NotifyInfo {

    private Long postId;
    private String title;
    private String content;
    private String writerName;
    private Page<CommentResposeDto> commentsResposeDto;

    public PostDetailResponsetDto(Long postId, String title,String content,
                                  String writerName,Page<CommentResposeDto> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.commentsResposeDto = comments;
    }

    @Override
    public String getReceiverName() {
        return getWriterName();
    }

    @Override
    public String getContent() {
        return getContent();
    }
}
