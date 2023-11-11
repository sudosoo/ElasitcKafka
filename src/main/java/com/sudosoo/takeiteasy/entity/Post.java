package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
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
    public static Post getInstance(CreatePostRequestDto createPostRequestDto){
        return  Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .build();
    }
    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public String getUserName(){
        if(this.member == null){
            throw new IllegalArgumentException("해당 포스트엔 유저가 등록되어 있지 않습니다.");
        }
        return this.member.getUserName();
    }
    public Long getCategoryId(){
        if(this.category == null){
            throw new IllegalArgumentException("해당 포스트엔 카테고리가 등록되어 있지 않습니다.");
        }
        return this.category.getId();
    }


}
