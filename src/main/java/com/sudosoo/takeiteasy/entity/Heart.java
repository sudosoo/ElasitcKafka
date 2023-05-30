package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    private Heart(Member member, Post post, Comment comment) {
        this.member = member;
        this.post = post;
        this.comment = comment;
    }

    public static Heart postLike(Post post , Member member){
        return Heart.builder()
                .post(post)
                .member(member)
                .build();
    }
    public static Heart commentLike(Comment comment , Member member){
        return Heart.builder()
                .comment(comment)
                .member(member)
                .build();
    }

    private void setPost(Post post) {
        this.post = post;
        post.getHearts().add(this);
    }
    private void setComment(Comment comment) {
        this.comment = comment;
        comment.getHearts().add(this);
    }

}
