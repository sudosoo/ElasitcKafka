package com.sudosoo.takeiteasy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RelatedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post mainPost;

    @ManyToOne
    @JoinColumn(name = "related_post_id")
    private Post relatedPost;

    private RelatedPost(Post mainPost, Post relatedPost) {
        this.mainPost = mainPost;
        this.relatedPost = relatedPost;
    }

    public static RelatedPost createRelatePost(Post mainPost , Post relatedPost){
        return new RelatedPost(mainPost,relatedPost);
    }


}