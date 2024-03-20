package com.sudosoo.takeItEasy.domain.entity;

import com.sudosoo.takeItEasy.domain.support.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static Category of(String categoryName){
        return new Category(categoryName);
    }


}