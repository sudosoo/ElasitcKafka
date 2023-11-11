package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping( "/category")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        Category category = categoryService.creatCategory(requestDto);
        log.info("New Category created :  categoryId {}", category.getId());
        return ResponseEntity.ok().build();
    }


}