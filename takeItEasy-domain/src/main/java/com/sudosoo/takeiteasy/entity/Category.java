package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeItEasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeItEasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeItEasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private boolean isDeleted = false;

    private Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(CreateCategoryRequestDto createCategoryRequestDto){
        return new Category(createCategoryRequestDto.getCategoryName());
    }
    public CategoryResponseDto toResponseDto(Category category, Page<PostTitleOnlyResponseDto> postTitleDtoPage){
        return new CategoryResponseDto(category.getCategoryName(),postTitleDtoPage);
    }

}