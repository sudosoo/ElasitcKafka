package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.post.PostSearchDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.application.service.ESPostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/elastic/post")
class ESPostController(val esPostService: ESPostService) {

    @GetMapping("/search")
    fun searchBy(
        @RequestBody
        requestDto: PostSearchDto,
        pageRequest: PageRequest) : Page<PostTitleOnlyResponseDto> {
        return esPostService.searchBy(requestDto,pageRequest)
    }
    @GetMapping("/export")
    fun exportToElasticsearch() = esPostService.exportToElasticsearch()


}