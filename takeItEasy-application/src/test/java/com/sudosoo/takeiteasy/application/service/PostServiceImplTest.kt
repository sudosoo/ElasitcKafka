package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.JpaService
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
import org.mockito.Mockito.mock
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

    private val testPost = Post( 1L, "제목", "내용", mockCategory, 1L, "작성자", 0, mutableListOf() )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(postRepository.save(any(Post::class.java))).thenReturn(testPost)
        `when`(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testPost))
    }

    @Test
    fun `게시글id로 게시글이 나와야 한다`() {
        val testPost = postService.getByPostId(anyLong())

        val expectedTitle = testRequestDto.title
        val actualTitle = testPost.title

        assertNotNull(testPost, "The created post should not be null")
        assertEquals(expectedTitle, actualTitle, "Expected Title: $expectedTitle, Actual Title: $actualTitle")
    }

    @Test
    fun `게시글에는 댓글들을 가질 수 있다`() {
        val commentMock1 = mock(Comment::class.java)
        val commentMock2 = mock(Comment::class.java)
        val commentMock3 = mock(Comment::class.java)
        val heart1 = mock(Heart::class.java)
        val heart2 = mock(Heart::class.java)
        val heart3 = mock(Heart::class.java)
        commentMock1.setHearts(heart1)
        commentMock1.setHearts(heart2)
        commentMock1.setHearts(heart3)

        val pageRequest: Pageable = PageRequest.of(0, 10)
        val commentPage: Page<Comment> = PageImpl(listOf(commentMock1, commentMock2, commentMock3))
        `when`(commentRepository.findCommentsByPostId(testPost.id, pageRequest)).thenReturn(commentPage)

        val result = postService.getPostDetailByPostId(testPost.id, pageRequest)
        assertEquals(commentPage.content.size, result.comments.size)
    }
}
