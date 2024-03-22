package com.sudosoo.takeItEasy.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.application.dto.comment.CommentResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.KafkaResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.kafkaMemberValidateRequestDto
import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.domain.repository.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.function.Supplier

@Service
@Transactional
class PostServiceImpl(
    val postRepository: PostRepository,
    val categoryService: CategoryService,
    val commentRepository: CommentRepository,
    val kafkaProducer: KafkaProducer,
    val redisService: RedisService
) : PostService {
    val objectMapper = ObjectMapper()

    override fun create(requestDto: CreatePostRequestDto): TestPostResponseDto {
        var kafkaResponseDto: KafkaResponseDto? = null
        try {
            val kafkaResult = kafkaProducer!!.replyRecord(kafkaMemberValidateRequestDto(requestDto.memberId))
            kafkaResponseDto = objectMapper.readValue(kafkaResult, KafkaResponseDto::class.java)
        } catch (e: ExecutionException) {
            e.stackTrace
        } catch (e: InterruptedException) {
            e.stackTrace
        } catch (e: IOException) {
            e.getStackTrace()
        }
        requireNotNull(kafkaResponseDto) { "kafka reply error" }

        val post: Post = Post.of(requestDto.title, requestDto.content)
        val category = categoryService.getById(requestDto.categoryId)
        post.setMemberIdAndWriter(kafkaResponseDto.memberId, kafkaResponseDto.memberName)
        post.setCategory(category)

        val result: Post = postRepository.save<Post>(post)

        //redis ReadRepository 데이터 삽입
        val responseDto = TestPostResponseDto(result)
        redisService.saveReadValue(responseDto)
        return responseDto
    }


    override fun redisTest(requestDto: PostRequestDto): TestPostResponseDto {
        val post: Post = Post.of(requestDto.title, requestDto.memberName)
        val category = categoryService.getById(requestDto.categoryId)
        post.setCategory(category)
        val result: Post = postRepository.save<Post>(post)
        return TestPostResponseDto(result)
    }


    override fun getByPostId(postId: Long): Post {
        return postRepository.findById(postId)
            .orElseThrow<IllegalArgumentException>(Supplier {
                IllegalArgumentException(
                    "Could not found post id : $postId"
                )
            })
    }

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

    @Transactional(readOnly = true)
    override fun allPost () : List<TestPostResponseDto>{
            val posts: List<Post> = postRepository.findAll()

        return posts.stream().map { o -> TestPostResponseDto(o)}.toList()
    }


    override fun getPaginationPost(pageable: Pageable): List<PostTitleOnlyResponseDto> {
        return postRepository.findAll(pageable).map { o -> PostTitleOnlyResponseDto(o) }.toList()
    }

    override fun createBatchPosts(count: Int): Post {
        return Post.builder()
            .title("Title$count")
            .content("content$count")
            .memberId(1L)
            .build()
    }
}