package com.sudosoo.takeItEasy.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.application.dto.comment.CommentResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.KafkaResponseDto
import com.sudosoo.takeItEasy.application.dto.kafka.kafkaMemberValidateRequestDto
import com.sudosoo.takeItEasy.application.dto.post.*
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.redis.RedisService
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
    val objectMapper: ObjectMapper,
    val redisService: RedisService
) : PostService {
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

        val post: Post = Post.of(requestDto)
        val category = categoryService.getById(requestDto.categoryId)
        post.setMemberIdAndWriter(kafkaResponseDto.memberId, kafkaResponseDto.memberName)
        post.setCategory(category)

        val result: Post = postRepository.save<Post>(post)

        //redis ReadRepository 데이터 삽입
        val responseDto: TestPostResponseDto = result.toResponseDto()
        redisService.saveReadValue(result.toResponseDto())
        return responseDto
    }


    override fun redisTest(requestDto: PostRequestDto): TestPostResponseDto {
        val post: Post = Post.testOf(requestDto)
        val category = categoryService.getById(requestDto.categoryId)
        post.setCategory(category)
        val result: Post = postRepository.save<Post>(post)
        return result.toResponseDto()
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
        //TODO MemberSetting 각 코멘트의 유저 이름을 찾아와서 넣어주기
        val responseCommentDtos: List<CommentResponseDto> =
            comments.stream().map{ obj -> obj.toResponseDto() }.toList()
        return post.toDetailDto(PageImpl(responseCommentDtos))
    }

    @Transactional(readOnly = true)
    override fun allPost () : List<TestPostResponseDto>{
            val posts: List<Post> = postRepository.findAll()

        return posts.stream().map { obj -> obj.toResponseDto()}.toList()
    }


    override fun getPaginationPost(pageable: Pageable): List<PostTitleOnlyResponseDto> {
        return postRepository.findAll(pageable).map { obj -> obj.toTitleOnlyDto() }.toList()
    }

    override fun createBatchPosts(count: Int): Post {
        return Post.builder()
            .title("Title$count")
            .content("content$count")
            .memberId(1L)
            .build()
    }
}