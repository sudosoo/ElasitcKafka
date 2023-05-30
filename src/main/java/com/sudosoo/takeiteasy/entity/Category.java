package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String CategoryName;
    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    private Category(String categoryName, List<Post> posts) {
        CategoryName = categoryName;
        this.posts = posts;
    }

    public void addPost(Post post){
        this.posts.add(post);

    }
}
