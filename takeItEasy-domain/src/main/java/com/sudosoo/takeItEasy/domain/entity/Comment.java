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

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Heart> hearts = new ArrayList<>();

    public Comment(Long id , String content, Long memberId, String writer, Post post, List<Heart> hearts) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.writer = writer;
        this.post = post;
        this.hearts = hearts;
    }

    public Comment(Long id, String content, String writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
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

    public String getWriter() {
        return this.writer;
    }

    public Post getPost() {
        return this.post;
    }

    public List<Heart> getHearts() {
        return this.hearts;
    }


}
