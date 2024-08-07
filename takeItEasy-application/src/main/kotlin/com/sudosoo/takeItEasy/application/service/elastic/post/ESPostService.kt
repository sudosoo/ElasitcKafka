package com.sudosoo.takeItEasy.application.service.elastic.post

import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.repository.post.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.post.PostRepository
import org.springframework.stereotype.Service

@Service
class ESPostService(
    private val esPostRepository : PostElasticRepository,
    private val postRepository : PostRepository
){
    fun exportToElasticsearch (){
        val posts = postRepository.findAll()
        for (post in posts){
            val esPost = EsPost(post)
            esPostRepository.save(esPost)
        }
    }





}
