package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostElasticServiceImpl (
    val postElasticRepository: PostElasticRepository

): PostElasticService{
    override fun create(createPostRequestDto: CreatePostRequestDto){

        val esPost = EsPost(
            createPostRequestDto.title,
            createPostRequestDto.content,
            createPostRequestDto.memberId,
            createPostRequestDto.categoryId,
            createPostRequestDto.writerName,
        )
        postElasticRepository.save(esPost)

    }

    override fun findByTitle(title: String, pageRequest: PageRequest): Page<EsPost> {
        return postElasticRepository.findByTitle(title, pageRequest)
    }

    override fun findByContent(content: String, pageRequest: PageRequest): Page<EsPost> {
        return postElasticRepository.findByContent(content, pageRequest)
    }


}
