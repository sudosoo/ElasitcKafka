package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.SetCategoryByPostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;

interface ServiceMediator {

    void setCategoryByPost(SetCategoryByPostRequestDto setCategoryByPostRequestDto);
    Category getCategoryByCategoryId (Long categoryId);
}
