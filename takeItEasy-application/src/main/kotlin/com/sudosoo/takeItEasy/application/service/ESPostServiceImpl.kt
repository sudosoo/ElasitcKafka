package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.post.PostSearchDto
import com.sudosoo.takeItEasy.application.dto.post.specification.PostSpec
import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ESPostServiceImpl(
    val esPostRepository : PostElasticRepository,
    val postRepository : PostRepository
) : ESPostService {

    val specific = PostSpec()

    fun findBySpecify(searchDto : PostSearchDto , pageRequest:PageRequest){

    }

    override fun exportPostsToElasticsearch (){
        val posts = postRepository.findAll()
        for (post in posts){
            val esPost = EsPost(post)
            esPostRepository.save(esPost)
        }
    }

    override fun searchBy(requestDto : PostSearchDto, pageRequest : PageRequest) {
        //TODO Search 구현
    }

}
