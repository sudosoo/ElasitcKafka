package com.sudosoo.takeiteasy.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListResponsetDto {

    private Long postId;
    private String title;
    private int likeCount;
    private String writerName;

    public PostListResponsetDto(Long postId, String title,int likeCount, String writerName) {
        this.postId = postId;
        this.title = title;
        this.likeCount = likeCount;
        this.writerName = writerName;
    }
}
