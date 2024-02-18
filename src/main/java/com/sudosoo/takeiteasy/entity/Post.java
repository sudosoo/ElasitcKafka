package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
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
        category.getPosts().add(this);
    }
    public void setMember(Long memberId) {
        this.memberId = memberId;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public PostTitleDto toTitleOnlyDto(){
        return new PostTitleDto(this.id, this.title, this.getHearts().size(),this.viewCount,this.writerName);
    }

    public PostDetailResponseDto toDetailDto(Page<CommentResposeDto> comments){
        return new PostDetailResponseDto(this.id, this.title,this.content,this.writerName,comments);
    }

}
