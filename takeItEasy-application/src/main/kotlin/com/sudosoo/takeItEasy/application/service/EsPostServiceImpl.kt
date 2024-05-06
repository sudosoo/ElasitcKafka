package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class EsPostServiceImpl(
    private val esPostRepository: PostElasticRepository,
    private val postRepository: PostRepository
) : EsPostService {
    override fun exportPostsToElasticsearch (){
        val posts = postRepository.findAll()
        for (post in posts){
            val esPost = EsPost(post)
            esPostRepository.save(esPost)
        }
    }
}
