package com.sudosoo.takeiteasy.dto.comment;

public class CommentResposeDto {

    private Long commentId;
    private String writerName;
    private String content;
    private int likeCount = 0;

    public CommentResposeDto(Long commentId, String writerName, String content, int likeCount) {
        this.commentId = commentId;
        this.writerName = writerName;
        this.content = content;
        this.likeCount = likeCount;
    }

}
