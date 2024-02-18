package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private HeartType heartType;

    @Builder
    public Heart(Long memberId, Post post, Comment comment,HeartType heartType) {
        this.memberId = memberId;
        this.post = post;
        this.comment = comment;
        this.heartType = heartType;
    }

    public static Heart getPostHeart(Post post , Long memberId){
        return Heart.builder()
                .post(post)
                .memberId(memberId)
                .heartType(HeartType.POST)
                .build();
    }
    public static Heart getCommentHeart(Comment comment , Long memberId){
        return Heart.builder()
                .comment(comment)
                .memberId(memberId)
                .heartType(HeartType.COMMENT)
                .build();
    }

    public void setPost(Post post) {
        this.post = post;
        post.getHearts().add(this);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getHearts().add(this);
    }

    public void unHeartPost() {
        if (post != null) {
            post.getHearts().remove(this);
            post = null;
        }
    }

    public void unHeartComment() {
        if (comment != null) {
            comment.getHearts().remove(this);
            comment = null;
        }
    }
}
