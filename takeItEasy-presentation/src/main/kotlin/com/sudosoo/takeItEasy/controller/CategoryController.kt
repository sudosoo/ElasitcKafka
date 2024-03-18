package com.sudosoo.takeItEasy.controller

import com.sudosoo.takeItEasy.dto.category.CategoryResponseDto
import com.sudosoo.takeItEasy.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.service.CategoryService
import com.sudosoo.takeiteasy.dto.category.CategoryResponseDto
import com.sudosoo.takeiteasy.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeiteasy.service.CategoryService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.awt.print.Pageable

@RestController
@RequestMapping("/api/category")
class CategoryController (
    val categoryService: CategoryService
) {

    @PostMapping( "/create", name = "createCategory")
    fun createCategory(@Valid @RequestBody requestDto: CreateCategoryRequestDto): ResponseEntity<Void> {
        categoryService.createCategory(requestDto)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/getPosts", name = "getPost")
    fun getPosts(
        @RequestParam categoryId: Long?,
        @RequestParam(required = false, defaultValue = "0", value = "page") pageNo: Int
    ): ResponseEntity<CategoryResponseDto> {
        var pageNo = pageNo
        pageNo = if ((pageNo == 0)) 0 else (pageNo - 1)
        val pageable: Pageable = PageRequest.of(pageNo, 10)
        return ResponseEntity(categoryService.getPostsByCategoryId(categoryId, pageable), HttpStatus.OK)
    }
}