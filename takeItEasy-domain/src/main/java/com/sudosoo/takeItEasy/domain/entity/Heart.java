package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Heart {
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

    public Heart(Long memberId, Post post, Comment comment, HeartType heartType) {
        this.memberId = memberId;
        this.post = post;
        this.comment = comment;
        this.heartType = heartType;
    }

    protected Heart() {
    }

    public static Heart getPostHeart(Post post, Long memberId) {
        return Heart.builder()
                .post(post)
                .memberId(memberId)
                .heartType(HeartType.POST)
                .build();
    }

    public static Heart getCommentHeart(Comment comment, Long memberId) {
        return Heart.builder()
                .comment(comment)
                .memberId(memberId)
                .heartType(HeartType.COMMENT)
                .build();
    }

    public static HeartBuilder builder() {
        return new HeartBuilder();
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

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Post getPost() {
        return this.post;
    }

    public Comment getComment() {
        return this.comment;
    }

    public HeartType getHeartType() {
        return this.heartType;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Heart)) return false;
        final Heart other = (Heart) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$memberId = this.getMemberId();
        final Object other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) return false;
        final Object this$post = this.getPost();
        final Object other$post = other.getPost();
        if (this$post == null ? other$post != null : !this$post.equals(other$post)) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) return false;
        final Object this$heartType = this.getHeartType();
        final Object other$heartType = other.getHeartType();
        if (this$heartType == null ? other$heartType != null : !this$heartType.equals(other$heartType)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Heart;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $memberId = this.getMemberId();
        result = result * PRIME + ($memberId == null ? 43 : $memberId.hashCode());
        final Object $post = this.getPost();
        result = result * PRIME + ($post == null ? 43 : $post.hashCode());
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        final Object $heartType = this.getHeartType();
        result = result * PRIME + ($heartType == null ? 43 : $heartType.hashCode());
        return result;
    }

    public static class HeartBuilder {
        private Long memberId;
        private Post post;
        private Comment comment;
        private HeartType heartType;

        HeartBuilder() {
        }

        public HeartBuilder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public HeartBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public HeartBuilder comment(Comment comment) {
            this.comment = comment;
            return this;
        }

        public HeartBuilder heartType(HeartType heartType) {
            this.heartType = heartType;
            return this;
        }

        public Heart build() {
            return new Heart(this.memberId, this.post, this.comment, this.heartType);
        }

        public String toString() {
            return "Heart.HeartBuilder(memberId=" + this.memberId + ", post=" + this.post + ", comment=" + this.comment + ", heartType=" + this.heartType + ")";
        }
    }
}
