package com.sudosoo.takeItEasy.application.service.post

import com.sudosoo.takeItEasy.application.commons.CommonService
import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.core.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.comment.CommentResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.KafkaResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.kafkaMemberValidateRequestDto
import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.application.dto.post.PostCQRSDto
import com.sudosoo.takeItEasy.application.dto.post.PostDetailResponseDto
import com.sudosoo.takeItEasy.application.dto.post.PostTitleOnlyResponseDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.application.service.category.CategoryService
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.comment.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import com.sudosoo.takeItEasy.domain.repository.post.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.util.concurrent.ExecutionException

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val categoryService: CategoryService,
    private val commentRepository: CommentRepository,
    private val kafkaProducer: KafkaProducer,
    private val redisService: RedisService
) : JpaService<Post, Long>, JpaSpecificService<Post, Long> {
    override var jpaRepository: BaseRepository<Post, Long> = postRepository
    override val jpaSpecRepository: BaseRepository<Post, Long> = postRepository
    val objectMapper = CommonService.getObjectMapper()

    fun defaultCreate(requestDto: CreatePostRequestDto) {
        val post = Post(requestDto.title, requestDto.content)
        val category = categoryService.getById(requestDto.categoryId)
        post.category = category
        save(post)
    }

    fun create(requestDto: CreatePostRequestDto): PostCQRSDto {
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
        val responseDto = PostCQRSDto(result)
        redisService.saveReadValue(responseDto)
        return responseDto
    }

    fun getByPostId(postId: Long): Post {
        return findById(postId) }

    @Transactional(readOnly = true)
    fun getPostDetailByPostId(postId: Long, pageRequest: Pageable): PostDetailResponseDto {
        val post: Post = postRepository.findById(postId)
            .orElseThrow{ IllegalArgumentException("해당 게시물이 존재 하지 않습니다.") }

        post.incrementViewCount()
        val comments = commentRepository.findCommentsByPostId(postId, pageRequest)
        val responseCommentDtos : MutableList<CommentResponseDto> =
            comments.stream().map{ o -> CommentResponseDto(o)}.toList()
        return PostDetailResponseDto(post,responseCommentDtos)
    }

    fun getPaginationPost(pageRequest: Pageable): Page<PostTitleOnlyResponseDto> {
        val posts: Page<Post> = postRepository.findAll(pageRequest)
        val titleOnlyPost = posts.stream().map{ o -> PostTitleOnlyResponseDto(o)}.toList()
        return PageImpl(titleOnlyPost, pageRequest, posts.totalElements)
    }

    fun createBatchPosts(count: Int): Post {
        return Post("Title$count",  "content$count",  count.toLong())
    }

    fun softDeletePost(postId: Long) {
        val post = findById(postId)
        post.delete()
        save(post)
    }

    fun postRepositoryRedisSynchronization() {
        //TODO 데이터량 많아지면 청크단위로 배치 작업 추가
        val posts = postRepository.findAll()
        val topicName = "PostResponseDto"
        redisService.resetRedisCacheWithAllPosts(topicName, posts.toList())
    }


}