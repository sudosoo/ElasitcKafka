package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.specification.PostSpec
import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class ESPostServiceImpl(
    private val esPostRepository : PostElasticRepository,
    private val postRepository : PostRepository
) : ESPostService{

    private val specific = PostSpec

    override fun exportToElasticsearch (){
        val posts = postRepository.findAll()
        for (post in posts){
            val esPost = EsPost(post)
            esPostRepository.save(esPost)
        }
    }





}
