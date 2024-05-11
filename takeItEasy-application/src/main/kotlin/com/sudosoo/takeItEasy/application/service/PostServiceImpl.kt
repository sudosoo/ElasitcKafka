package com.sudosoo.takeItEasy.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.comment.CommentResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.KafkaResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.kafkaMemberValidateRequestDto
import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.application.dto.post.specification.PostSpec
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.domain.entity.EsPost
import com.sudosoo.takeItEasy.domain.entity.Event
//import com.sudosoo.takeItEasy.domain.entity.
import com.sudosoo.takeItEasy.domain.repository.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
//import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.query.Query.findAll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.util.concurrent.ExecutionException

@Service
@Transactional
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val categoryService: CategoryService,
    private val commentRepository: CommentRepository,
    private val kafkaProducer: KafkaProducer,
    private val redisService: RedisService
) : PostService , JpaService<Post, Long>, JpaSpecificService<Post, Long>{
    override var jpaRepository: BaseRepository<Post, Long> = postRepository
    override val jpaSpecRepository: BaseRepository<Post, Long> = postRepository
    val objectMapper = ObjectMapper()

    override fun defaultCreate(requestDto: CreatePostRequestDto) {
        val post = Post(requestDto.title, requestDto.content)
        val category = categoryService.getById(requestDto.categoryId)
        post.category = category
        save(post)
    }

    override fun create(requestDto: CreatePostRequestDto): TestPostResponseDto {
        var kafkaResponseDto: KafkaResponseDto? = null
        try {
            val kafkaResult = kafkaProducer.replyRecord(kafkaMemberValidateRequestDto(requestDto.memberId))
            kafkaResponseDto = objectMapper.readValue(kafkaResult, KafkaResponseDto::class.java)
        } catch (e: ExecutionException ) {
            e.stackTrace
        } catch (e: InterruptedException) {
            e.stackTrace
        } catch (e: IOException) {
            e.stackTrace
        }
        requireNotNull(kafkaResponseDto) { "kafka reply error" }

        val post = Post(requestDto.title, requestDto.content)
        val category = categoryService.getById(requestDto.categoryId)
        post.setMemberIdAndWriter(kafkaResponseDto.memberId, kafkaResponseDto.memberName)
        post.category = category
        val result: Post = postRepository.save(post)

        //redis ReadRepository 데이터 삽입
        val responseDto = TestPostResponseDto(result)
        redisService.saveReadValue(responseDto)
        return responseDto
    }

    override fun getByPostId(postId: Long): Post {
        return findById(postId) }

    @Transactional(readOnly = true)
    override fun getPostDetailByPostId(postId: Long, pageRequest: Pageable): PostDetailResponseDto {
        val post: Post = postRepository.findById(postId)
            .orElseThrow{ IllegalArgumentException("해당 게시물이 존재 하지 않습니다.") }
        post.incrementViewCount()

        val comments: Page<Comment> = commentRepository.findCommentsByPostId(postId, pageRequest)
        val responseCommentDtos : MutableList<CommentResponseDto> =
            comments.stream().map{ o -> CommentResponseDto(o) }.toList()

        return PostDetailResponseDto(post,responseCommentDtos)
    }

    override fun getPaginationPost(pageRequest: Pageable): Page<PostTitleOnlyResponseDto> {
        val posts: Page<Post> = postRepository.findAll(pageRequest)
        val titleOnlyPost = posts.stream().map{ o -> PostTitleOnlyResponseDto(o)}.toList()
        return PageImpl(titleOnlyPost, pageRequest, posts.totalElements)
    }

    override fun createBatchPosts(count: Int): Post {
        return Post("Title$count",  "content$count",  count.toLong())
    }

    override fun softDeletePost(postId: Long) {
        val post = findById(postId)
        post.delete()
        save(post)
    }


}