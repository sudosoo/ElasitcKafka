package com.sudosoo.takeItEasy.presentation.controller.post

import com.sudosoo.takeItEasy.application.service.elastic.post.ESPostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/elastic/post")
class ESPostController(val esPostService: ESPostService) {

    @GetMapping("/export")
    fun exportToElasticsearch() = esPostService.exportToElasticsearch()

}