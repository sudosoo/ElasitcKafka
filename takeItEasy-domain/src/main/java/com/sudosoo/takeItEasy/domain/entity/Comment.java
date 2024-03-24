package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Comment(Long id , String content, Long memberId, String writerName, Post post, List<Heart> hearts) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.writerName = writerName;
        this.post = post;
        this.hearts = hearts;
    }

    public Comment(Long id, String content, String writerName) {
        this.id = id;
        this.content = content;
        this.writerName = writerName;
    }

    public Comment(String content) {
        this.content = content;
    }

    protected Comment() {}

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setHearts(Heart heart) {
        this.hearts.add(heart);
        heart.setComment(this);
    }

    public Long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public String getWriterName() {
        return this.writerName;
    }

    public Post getPost() {
        return this.post;
    }

    public List<Heart> getHearts() {
        return this.hearts;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Comment)) return false;
        final Comment other = (Comment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$memberId = this.getMemberId();
        final Object other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) return false;
        final Object this$writerName = this.getWriterName();
        final Object other$writerName = other.getWriterName();
        if (this$writerName == null ? other$writerName != null : !this$writerName.equals(other$writerName))
            return false;
        final Object this$post = this.getPost();
        final Object other$post = other.getPost();
        if (this$post == null ? other$post != null : !this$post.equals(other$post)) return false;
        final Object this$hearts = this.getHearts();
        final Object other$hearts = other.getHearts();
        if (this$hearts == null ? other$hearts != null : !this$hearts.equals(other$hearts)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Comment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $memberId = this.getMemberId();
        result = result * PRIME + ($memberId == null ? 43 : $memberId.hashCode());
        final Object $writerName = this.getWriterName();
        result = result * PRIME + ($writerName == null ? 43 : $writerName.hashCode());
        final Object $post = this.getPost();
        result = result * PRIME + ($post == null ? 43 : $post.hashCode());
        final Object $hearts = this.getHearts();
        result = result * PRIME + ($hearts == null ? 43 : $hearts.hashCode());
        return result;
    }



}
