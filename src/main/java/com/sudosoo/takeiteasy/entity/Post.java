package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponsetDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

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

    public void setHearts(Heart heart) {
        this.hearts.add(heart);
        heart.setPost(this);
    }
    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public String getMemberName(){
        if(this.member == null){
            throw new IllegalArgumentException("해당 포스트엔 유저가 등록되어 있지 않습니다.");
        }
        return this.member.getMemberName();
    }
    public void incrementViewCount() {
        this.viewCount++;
    }

    public PostTitleDto toTitleOnlyDto(){
        return new PostTitleDto(this.getId(), this.getTitle(), this.getHearts().size(),this.getViewCount(),this.getMemberName());
    }

    public PostDetailResponsetDto toDetailDto(Page<CommentResposeDto> comments){
        return new PostDetailResponsetDto(this.getId(), this.getTitle(),this.getContent(),this.getMemberName(),comments);
    }

}
