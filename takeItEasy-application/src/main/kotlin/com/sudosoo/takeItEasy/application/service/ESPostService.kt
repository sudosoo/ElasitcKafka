package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.PostSearchDto
import com.sudosoo.takeItEasy.domain.entity.EsPost
import org.springframework.data.domain.PageRequest

internal interface ESPostService {
    fun exportPostsToElasticsearch()
    fun searchBy(requestDto : PostSearchDto, pageRequest : PageRequest)
}