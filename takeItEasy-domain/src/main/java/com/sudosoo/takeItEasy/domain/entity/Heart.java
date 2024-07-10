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

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private HeartType heartType;

    public Heart(Long id, Long memberId, Post post, Comment comment, HeartType heartType) {
        this.id = id;
        this.memberId = memberId;
        this.post = post;
        this.comment = comment;
        this.heartType = heartType;
    }

    private Heart(Long memberId, Comment comment) {
        this.memberId = memberId;
        this.comment = comment;
        this.heartType = HeartType.COMMENT;

    }

    private Heart(Long memberId, Post post) {
        this.memberId = memberId;
        this.post = post;
        this.heartType = HeartType.POST;
    }

    public static Heart getPostHeart(Long memberId , Post post) {
        return new Heart(memberId, post);
    }

    public static Heart getCommentHeart(Long memberId , Comment comment) {
        return new Heart(memberId, comment);
    }

    protected Heart() {
    }

    public void setPost(Post post) {
        this.post = post;
        post.getHearts().add(this);
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

    public void setComment(Comment comment) {
        this.comment = comment;
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

}
