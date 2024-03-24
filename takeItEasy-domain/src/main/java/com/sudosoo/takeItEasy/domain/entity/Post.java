package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post", indexes = {
        @Index(name = "idx_title", columnList = "title")
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Long memberId;
    private String writerName;

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    public Post(Long id, String title, String content, Category category, Long memberId, String writerName, int viewCount, List<Heart> hearts) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.memberId = memberId;
        this.writerName = writerName;
        this.viewCount = viewCount;
        this.hearts = hearts;
    }

    public Post(String title, String content, String writerName) {
        this.title = title;
        this.content = content;
        this.writerName = writerName;
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public Post() {
    }

    public void setMemberIdAndWriter(Long memberId, String writerName) {
        this.memberId = memberId;
        this.writerName = writerName;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }


    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Category getCategory() {
        return this.category;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public String getWriterName() {
        return this.writerName;
    }

    public List<Heart> getHearts() {
        return this.hearts;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public int getViewCount() {
        return this.viewCount;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Post)) return false;
        final Post other = (Post) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$memberId = this.getMemberId();
        final Object other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) return false;
        final Object this$writerName = this.getWriterName();
        final Object other$writerName = other.getWriterName();
        if (this$writerName == null ? other$writerName != null : !this$writerName.equals(other$writerName))
            return false;
        final Object this$hearts = this.getHearts();
        final Object other$hearts = other.getHearts();
        if (this$hearts == null ? other$hearts != null : !this$hearts.equals(other$hearts)) return false;
        final Object this$comments = this.getComments();
        final Object other$comments = other.getComments();
        if (this$comments == null ? other$comments != null : !this$comments.equals(other$comments)) return false;
        if (this.getViewCount() != other.getViewCount()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Post;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $memberId = this.getMemberId();
        result = result * PRIME + ($memberId == null ? 43 : $memberId.hashCode());
        final Object $writerName = this.getWriterName();
        result = result * PRIME + ($writerName == null ? 43 : $writerName.hashCode());
        final Object $hearts = this.getHearts();
        result = result * PRIME + ($hearts == null ? 43 : $hearts.hashCode());
        final Object $comments = this.getComments();
        result = result * PRIME + ($comments == null ? 43 : $comments.hashCode());
        result = result * PRIME + this.getViewCount();
        return result;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
