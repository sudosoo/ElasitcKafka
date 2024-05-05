//package com.sudosoo.takeItEasy.application.service
//
//import com.sudosoo.takeItEasy.domain.entity.EsPost
//import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
//import com.sudosoo.takeItEasy.domain.repository.PostRepository
//import org.springframework.stereotype.Service
//
//@Service
//class EsPostServiceImpl(
//    private val esPostRepository: PostElasticRepository,
//    private val postRepository: PostRepository
//) : EsPostService {
//    override fun exportPostsToElasticsearch (){
//        val posts = postRepository.findAll()
//        for (post in posts){
//            val esPost = EsPost(post.id, post.title, post.content ,post.isDeleted, post.deletedAt, post.category.id, post.writerName ,post.hearts, post.comments ,post.viewCount)
//            esPostRepository.save(esPost)
//        }
//    }
//}