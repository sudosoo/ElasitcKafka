package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.post.PostSearchDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.application.dto.post.specification.PostSpec
import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ESPostServiceImpl(
    private val esPostRepository : PostElasticRepository,
    private  val postRepository : PostRepository
) : ESPostService,JpaSpecificService<EsPost,Long> {

    override val jpaSpecRepository: BaseRepository<EsPost, Long> = esPostRepository
    private val specific = PostSpec

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
        val specification = specific.bySearchDto(requestDto)
        val posts = findAllBy(
            specification = specification,
            pageable = pageRequest)
        val count = countBy(specification)
        posts.map {
            PostTitleOnlyResponseDto(it)

        }

        }



}
