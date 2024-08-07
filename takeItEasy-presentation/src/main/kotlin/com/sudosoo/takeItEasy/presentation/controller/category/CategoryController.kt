package com.sudosoo.takeItEasy.presentation.controller.category

import com.sudosoo.takeItEasy.application.dto.category.CreateCategoryRequestDto
import com.sudosoo.takeItEasy.application.dto.category.UpdateCategoryRequestDto
import com.sudosoo.takeItEasy.application.service.category.CategoryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/category")
class CategoryController (val categoryService: CategoryService){

    @PostMapping( "/create", name = "createCategory")
    fun create(@Valid @RequestBody requestDto: CreateCategoryRequestDto): ResponseEntity<Void> {
        categoryService.create(requestDto)
        return ResponseEntity.ok().build()
    }

    @PutMapping( "/create", name = "createCategory")
    fun updateTitle(@Valid @RequestBody requestDto: UpdateCategoryRequestDto): ResponseEntity<Void> {
        categoryService.updateTitle(requestDto)
        return ResponseEntity.ok().build()
    }


}