package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto;
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto;
import com.sudosoo.takeiteasy.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping(value = "/create" , name = "createCategory")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {

        categoryService.creatCategory(requestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getPosts" , name = "getPost")
    public ResponseEntity<CategoryResponseDto> getPosts
            (@RequestParam Long categoryId,
             @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        Pageable pageable = PageRequest.of(pageNo,10);
        return new ResponseEntity<>(categoryService.getPostsByCategoryId(categoryId,pageable), HttpStatus.OK);
    }

}