package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

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
    private Post(Long id,String title, String content, Category category, Long memberId,int viewCount, List<Heart> hearts) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.memberId = memberId;
        this.viewCount = viewCount;
        this.hearts = hearts;
    }
    public static Post of(CreatePostRequestDto createPostRequestDto){
        return  Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .viewCount(0)
                .build();
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void setMember(Long memberId,String writerName) {
        this.memberId = memberId;
        this.writerName = writerName;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public PostTitleOnlyResponseDto toTitleOnlyDto(){
        return new PostTitleOnlyResponseDto(this.id, this.title, this.getHearts().size(),this.viewCount,this.writerName);
    }

    public PostDetailResponseDto toDetailDto(Page<CommentResponseDto> comments){
        return new PostDetailResponseDto(this.id, this.title,this.content,this.writerName,comments);
    }

}
