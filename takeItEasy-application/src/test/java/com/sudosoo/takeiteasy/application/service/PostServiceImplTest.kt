package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.application.service.CategoryService
import com.sudosoo.takeItEasy.application.service.PostServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Category
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Heart
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.PostRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.*

class PostServiceImplTest{
    @Mock
    lateinit var categoryService: CategoryService

    @Mock
    lateinit var commentRepository: CommentRepository

    @Mock
    lateinit var kafkaProducer: KafkaProducer

    @Mock
    lateinit var redisService: RedisService

    @Mock
    lateinit var postRepository: PostRepository
    @Mock
    lateinit var jpaService: JpaService<Post, Long>

    @InjectMocks
    lateinit var postService: PostServiceImpl

    val mockCategory = Category("카테고리")
    private val testRequestDto = CreatePostRequestDto("제목", "내용", 1L, 1L)

    private val resultPost = Post( 1L, "제목", "내용", mockCategory, 1L, "작성자", 0, mutableListOf() )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(postRepository.save(any(Post::class.java))).thenReturn(resultPost)
        `when`(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(resultPost))
    }

    @Test
    fun `게시글id로 게시글 가져오기`() {
        val testPost = postService.getByPostId(1L)

        val expectedTitle = testRequestDto.title
        val actualTitle = testPost.title

        assertNotNull(testPost, "The created post should not be null")
        assertEquals(expectedTitle, actualTitle, "Expected Title: $expectedTitle, Actual Title: $actualTitle")
    }

    @Test
    fun `게시글id로 게시글 상세 내용가져오기 `() {
        val commentMock1 = Comment(1L, "testM1", "test1")
        val commentMock2 = Comment(2L, "testM2", "test2")
        val commentMock3 = Comment(3L, "testM3", "test3")
        val heart1 = Heart.getCommentHeart( 1L,commentMock1)
        val heart2 = Heart.getCommentHeart( 2L,commentMock2)
        val heart3 = Heart.getCommentHeart( 1L,commentMock3)
        commentMock1.setHearts(heart1)
        commentMock1.setHearts(heart2)
        commentMock1.setHearts(heart3)

        val pageRequest: Pageable = PageRequest.of(0, 10)
        val commentPage: Page<Comment> = PageImpl(listOf(commentMock1, commentMock2, commentMock3))
        `when`(commentRepository.findCommentsByPostId(1L, pageRequest)).thenReturn(commentPage)

        val result = postService.getPostDetailByPostId(1L, pageRequest)

        assertEquals(commentPage.content.size, result.comments.size)
    }
}
