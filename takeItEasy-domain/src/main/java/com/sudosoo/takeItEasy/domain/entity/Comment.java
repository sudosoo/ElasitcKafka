package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    private Long memberId;
    private String writerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    private Comment(String content, Long memberId,String writerName, Post post, List<Heart> hearts) {
        this.content = content;
        this.memberId = memberId;
        this.writerName = writerName;
        this.post = post;
        this.hearts = hearts;
    }


    public static Comment of(String content){
        return Comment.builder()
                .content(content)
                .build();
    }
    public void setMember(Long memberId){
        this.memberId = memberId;
    }
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }


}
