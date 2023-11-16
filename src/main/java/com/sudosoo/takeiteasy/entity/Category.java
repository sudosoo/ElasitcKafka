package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.common.BaseEntity;
import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String categoryName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        this.posts.add(post);
        post.setCategory(this);
    }
    private Category(CreateCategoryRequestDto createCategoryRequestDto) {
        this.categoryName = createCategoryRequestDto.getCategoryName();
    }

    public static Category of(CreateCategoryRequestDto createCategoryRequestDto){
        return new Category(createCategoryRequestDto);
    }
}
