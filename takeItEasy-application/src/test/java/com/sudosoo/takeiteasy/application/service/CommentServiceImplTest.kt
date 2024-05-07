package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.application.service.CommentServiceImpl
import com.sudosoo.takeItEasy.application.service.PostService
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.CommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*
import java.util.*

internal class CommentServiceImplTest {
    @Mock
    lateinit var commentRepository: CommentRepository
    @Mock
    lateinit var postService: PostService
    @Mock
    lateinit var jpaService: JpaService<Comment, Long>
    @InjectMocks
    lateinit var commentService: CommentServiceImpl

    private val createCommentRequestDto = CreateCommentRequestDto(1L, 1L, "tent")
    private val testComment = Comment(createCommentRequestDto.content)
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val mockPost = mock(Post::class.java)
        `when`(postService.getByPostId(anyLong())).thenReturn(mockPost)
    }

    @Test
    fun `코멘트 만들기`() {
        //given
        `when`<Any>(commentRepository.save(ArgumentMatchers.any())).thenReturn(testComment)

        //when
        commentService.create(createCommentRequestDto)

        //then
        verify(commentRepository, times(1)).save(any())

    }

    @Test
    fun `코멘트 아이디로 코멘트 가져오기`() {
        // given
        `when`(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testComment))

        // when
        val resultComment = commentService.getByCommentId(1L)

        // then
        val expectedCommentContent = createCommentRequestDto.content
        val actualCommentContent = resultComment.content

        verify(commentRepository, times(1)).findById(any())
        assertThat(actualCommentContent.equals(expectedCommentContent))
    }
}