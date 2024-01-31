package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment",orphanRemoval = true , cascade = ALL)
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    private Comment(String content, Member member, Post post, List<Heart> hearts) {
        this.content = content;
        this.member = member;
        this.post = post;
        this.hearts = hearts;
    }
    public static Comment of(CreateCommentRequestDto createCommentRequestDto){
        return Comment.builder()
                .content(createCommentRequestDto.getContent())
                .build();
    }
    public void setMember(Member member){
        this.member = member;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public String getUserName() {
        if(this.member == null){
            throw new IllegalArgumentException("사용자가 등록되어 있지 않습니다.");
        }
        return this.member.getMemberName();
    }

    public CommentResponseDto toResponseDto(){
        return new CommentResponseDto(this.getId(),this.getUserName(),this.getContent(),this.getHearts().size());
    }

}
