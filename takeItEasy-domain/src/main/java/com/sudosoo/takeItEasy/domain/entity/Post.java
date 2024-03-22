package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Setter
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

    @Builder
    private Post(Long id,String title, String content, Category category, Long memberId,String writerName,int viewCount, List<Heart> hearts) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.memberId = memberId;
        this.writerName = writerName;
        this.viewCount = viewCount;
        this.hearts = hearts;
    }
    public static Post of(String title, String content){
        return  Post.builder()
                .title(title)
                .content(content)
                .viewCount(0)
                .build();
    }

    public static Post testOf(String title, String content, String memberName){
        return  Post.builder()
                .title(title)
                .content(content)
                .writerName(memberName)
                .viewCount(0)
                .build();
    }

    public void setMemberIdAndWriter(Long memberId,String writerName) {
        this.memberId = memberId;
        this.writerName = writerName;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }


}
