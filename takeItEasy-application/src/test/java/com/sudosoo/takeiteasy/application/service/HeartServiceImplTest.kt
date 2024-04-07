package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.heart.CommentHeartRequestDto
import com.sudosoo.takeItEasy.application.dto.heart.PostHeartRequestDto
import com.sudosoo.takeItEasy.application.service.CommentService
import com.sudosoo.takeItEasy.application.service.HeartServiceImpl
import com.sudosoo.takeItEasy.application.service.PostService
import com.sudosoo.takeItEasy.domain.entity.*
import com.sudosoo.takeItEasy.domain.repository.HeartRepository
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class HeartServiceImplTest {
    @Mock
    lateinit var heartRepository: HeartRepository
    @Mock
    lateinit var postService: PostService
    @Mock
    lateinit var commentService: CommentService
    @Mock
    lateinit var jpaService: JpaService<Heart, Long>
    @InjectMocks
    lateinit var heartService: HeartServiceImpl

    private val testCategory = Category("카테고리")
    private val testPost = Post(1L, "제목", "내용", testCategory, 1L, "작성자", 0, mutableListOf())
    private val testComment = Comment(1L, "내용", 1L, "작성자",testPost, mutableListOf())
    private val testCommentHeart = Heart(1L,1L,testPost, testComment, HeartType.COMMENT)
    private val testPostHeart = Heart(1L,1L,testPost, testComment, HeartType.POST)


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(postService.getByPostId(anyLong())).thenReturn(testPost)
        `when`(commentService.getByCommentId(anyLong())).thenReturn(testComment)
    }

    @Test
    fun `게시판 하트가 저장 된다`() {
        //given
        val postHeartRequestDto = PostHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any())).thenReturn(testPostHeart)
        `when`(heartRepository.existsByMemberIdAndPost(anyLong(), any())).thenReturn(false)
        `when`(heartRepository.findByMemberIdAndPost(anyLong(), any())).thenReturn(Optional.ofNullable(testPostHeart))

        //when
        val heart = heartService.createPostHeart(postHeartRequestDto)

        //then
        assertNotNull(heart, "The actual heart should not be null")
    }
    fun `같은 게시판하트가 저장되면 에러가 난다`(){
        val postHeartRequestDto = PostHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any())).thenReturn(testPostHeart)
        `when`(heartRepository.existsByMemberIdAndPost(anyLong(), any())).thenReturn(true)
        `when`(heartRepository.findByMemberIdAndPost(anyLong(), any())).thenReturn(Optional.ofNullable(testPostHeart))


        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            heartService.createPostHeart(postHeartRequestDto)
        }
        //then
        assert(exception.message == "Duplicated Like !")
    }

    @Test
    fun `댓글하트가 저장 된다`() {
        //given
        val commentHeartRequestDto = CommentHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any())).thenReturn(testCommentHeart)
        `when`(heartRepository.existsByMemberIdAndComment(anyLong(), any())).thenReturn(false)
        `when`(heartRepository.findByMemberIdAndComment(1L, testComment)).thenReturn(Optional.ofNullable(testCommentHeart))
        //when
        val heart = heartService.createCommentHeart(commentHeartRequestDto)

        //then
        assertNotNull(heart, "The actual heart should not be null")
    }

    @Test
    fun `같은 댓글하트가 등록되면 에러가 난다`() {
        //given
        val commentHeartRequestDto = CommentHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any())).thenReturn(testCommentHeart)
        `when`(heartRepository.existsByMemberIdAndComment(anyLong(), any())).thenReturn(true)
        `when`(heartRepository.findByMemberIdAndComment(1L, testComment)).thenReturn(Optional.ofNullable(testCommentHeart))
        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            heartService.createCommentHeart(commentHeartRequestDto)
        }
        //then
        assert(exception.message == "Duplicated Like !")
    }

    @Test
    fun `게시판 하트가 지워진다`() {
        //given
        val postHeartRequestDto = PostHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any(Heart::class.java))).thenReturn(testPostHeart)
        `when`(heartRepository.findByMemberIdAndPost(anyLong(), any()))
            .thenReturn(Optional.ofNullable(testPostHeart))
        //when
        assertDoesNotThrow { heartService.postDisHeart(postHeartRequestDto) }

        //then
        verify(heartRepository, times(1)).deleteById(anyLong())
    }

    @Test
    fun `게시판하트가 등록되어 있지 않으면 지울 수 없다`() {
        //given
        val postHeartRequestDto = PostHeartRequestDto(1L, 1L)
        `when`(heartRepository.save(any(Heart::class.java))).thenReturn(testPostHeart)
        `when`(heartRepository.findByMemberIdAndPost(anyLong(), any(Post::class.java)))
            .thenReturn(Optional.empty())
        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            heartService.postDisHeart(postHeartRequestDto)
        }

        //then
        verify(heartRepository, never()).deleteById(any())
        assert(exception.message == "Could not find PostHeart id")
    }

    @Test
    fun `댓글 하트 지우기`() {
        //given
        val commentHeartRequestDto = CommentHeartRequestDto(1L, 1L)

        `when`(heartRepository.save(any(Heart::class.java))).thenReturn(testCommentHeart)
        `when`(heartRepository.findByMemberIdAndComment(anyLong(), any(Comment::class.java)))
            .thenReturn(Optional.ofNullable(testCommentHeart))

        //when
        assertDoesNotThrow { heartService.commentDisHeart(commentHeartRequestDto) }

        //then
        verify(heartRepository, times(1)).deleteById(anyLong())
    }
    @Test
    fun `댓글하트가 등록되어 있지 않으면 지울 수 없다`() {
        //given
        val requestDto = CommentHeartRequestDto(1L, 1L)

        `when`(heartRepository.save(any(Heart::class.java))).thenReturn(testCommentHeart)
        `when`(heartRepository.findByMemberIdAndComment(anyLong(), any(Comment::class.java)))
            .thenReturn(Optional.empty())

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            heartService.commentDisHeart(requestDto)
        }

        //then
        verify(heartRepository, never()).deleteById(any())
        assert(exception.message == "Could not find CommentHeart id")
    }
}