package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "relatedPost")
    private List<Post> relatedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime creatTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @Builder
    private Post(String title, String content, Category category, Member member, Integer viewCount, List<Heart> hearts) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.viewCount = viewCount;
        this.hearts = hearts;
    }
    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }
    public void setRelatedPost(Post post){
        post.relatedPosts.add(this);
    }


}