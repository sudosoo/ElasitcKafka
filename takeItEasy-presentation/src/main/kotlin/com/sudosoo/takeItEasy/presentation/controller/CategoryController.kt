package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.service.CategoryService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/category")
class CategoryController (val categoryService: CategoryService ){

    @PostMapping( "/create", name = "createCategory")
    fun createCategory(@Valid @RequestBody requestDto: CreateCategoryRequestDto): ResponseEntity<Void> {
        categoryService.create(requestDto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/getPosts", name = "getPost")
    fun getPosts(
        @RequestParam categoryId: Long,
        @RequestParam(required = false, defaultValue = "0", value = "page") pageNo: Int
    ): ResponseEntity<CategoryResponseDto> {
        var pageNo = pageNo
        pageNo = if ((pageNo == 0)) 0 else (pageNo - 1)
        val pageable: PageRequest = PageRequest.of(pageNo, 10)
        return ResponseEntity(categoryService.getPosts(categoryId, pageable), HttpStatus.OK)
    }
}