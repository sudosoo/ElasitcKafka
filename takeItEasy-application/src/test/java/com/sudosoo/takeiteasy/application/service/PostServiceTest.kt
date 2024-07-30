package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.post.CreatePostRequestDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.application.redis.RedisService
import com.sudosoo.takeItEasy.application.service.category.CategoryService
import com.sudosoo.takeItEasy.application.service.post.PostService
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
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

class PostServiceTest{
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
    lateinit var postService: PostService

    val mockCategory = Category("카테고리")

    private val testRequestDto = CreatePostRequestDto("제목", "내용", 1L, "작성자",1L)

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
    fun `게시물 id를 넣으면 관련된 댓글이 나와야 한다`() {
        val commentMock1 = Comment(1L,"댓글1", 1L, "작성자1", testPost, mutableListOf())
        val commentMock2 = Comment(2L,"댓글2", 1L, "작성자2", testPost, mutableListOf())
        val commentMock3 = Comment(3L,"댓글3", 1L, "작성자3", testPost, mutableListOf())
        val heart1 = mock(Heart::class.java)
        val heart2 = mock(Heart::class.java)
        val heart3 = mock(Heart::class.java)
        commentMock1.setHearts(heart1)
        commentMock1.setHearts(heart2)
        commentMock1.setHearts(heart3)

        val pageRequest = PageRequest.of(0, 10)
        val commentPage = PageImpl(listOf(commentMock1, commentMock2, commentMock3))
        `when`(commentRepository.findCommentsByPostId(testPost.id, pageRequest)).thenReturn(commentPage)

        val result = postService.getPostDetailByPostId(testPost.id, pageRequest)
        assertEquals(commentPage.content.size, result.comments.size)
    }
}
