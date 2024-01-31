package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post", indexes = {
        @Index(name = "idx_title", columnList = "title")
})
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post",orphanRemoval = true, cascade = ALL)
    private List<Heart> hearts = new ArrayList<>();

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Builder
    private Post(Long id,String title, String content, Category category, Member member,int viewCount, List<Heart> hearts) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.viewCount = viewCount;
        this.hearts = hearts;
    }
    public static Post testOf(Long id,String title, String content, Category category, Member member, int viewCount, List<Heart> hearts){
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .member(member)
                .viewCount(viewCount)
                .hearts(hearts)
                .build();
    }
    public static Post of(CreatePostRequestDto createPostRequestDto){
        return  Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .viewCount(0)
                .build();
    }

    public void setHeart(Heart heart) {
        this.hearts.add(heart);
        heart.setPost(this);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getMemberName(){
        if(this.member == null){
            throw new IllegalArgumentException("해당 게시물에 유저가 등록 되어 있지 않습니다.");
        }
        return this.member.getMemberName();
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public PostTitleOnlyResponseDto toTitleOnlyDto(){
        return new PostTitleOnlyResponseDto(this.getId(), this.getTitle(), this.getHearts().size(),this.getViewCount(),this.getMemberName());
    }

    public PostDetailResponseDto toDetailDto(Page<CommentResponseDto> comments){
        return new PostDetailResponseDto(this.getId(), this.getTitle(),this.getContent(),this.getMemberName(),comments);
    }

}
