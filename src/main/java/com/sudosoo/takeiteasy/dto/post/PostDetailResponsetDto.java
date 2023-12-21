package com.sudosoo.takeiteasy.dto.post;

import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import com.sudosoo.takeiteasy.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PostDetailResponsetDto {

    private Long postId;
    private String title;
    private String content;
    private int likeCount = 0;
    private String writerName;
    private Page<CommentResposeDto> commentResposeDtoList;

    public PostDetailResponsetDto(Long postId, String title,String content, int likeCount,
                                  String writerName,Page<CommentResposeDto> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.writerName = writerName;
        this.commentResposeDtoList = comments;
    }
}
