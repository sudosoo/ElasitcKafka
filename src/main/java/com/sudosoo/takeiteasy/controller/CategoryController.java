package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping( "/category")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryService.creatCategory(requestDto);
        return ResponseEntity.ok().build();
    }


}