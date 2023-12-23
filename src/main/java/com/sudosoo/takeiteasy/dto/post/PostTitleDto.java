package com.sudosoo.takeiteasy.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTitleDto {

    private Long postId;
    private String title;
    private int likeCount;
    private int viewCount;
    private String writerName;

    public PostTitleDto(Long postId, String title, int likeCount,int viewCount, String writerName) {
        this.postId = postId;
        this.title = title;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.writerName = writerName;
    }
}
