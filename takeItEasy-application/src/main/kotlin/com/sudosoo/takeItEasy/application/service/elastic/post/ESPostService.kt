package com.sudosoo.takeItEasy.application.service.elastic.post

import com.sudosoo.takeItEasy.application.dto.post.PostSearchDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.domain.entity.EsPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface ESPostService {
    fun exportToElasticsearch()
}
