package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.application.service.comment.CommentService
import com.sudosoo.takeItEasy.application.service.post.PostService
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

internal class CommentServiceTest {
    @Mock
    lateinit var commentRepository: CommentRepository
    @Mock
    lateinit var postService: PostService
    @Mock
    lateinit var jpaService: JpaService<Comment, Long>
    @InjectMocks
    lateinit var commentService: CommentService

    private val createCommentRequestDto = CreateCommentRequestDto(1L, 1L, "tent")
    private val testComment = Comment(createCommentRequestDto.content)
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val mockPost = mock(Post::class.java)
        `when`(postService.getByPostId(anyLong())).thenReturn(mockPost)
    }

    @Test
    fun `댓글이 만들어져야 한다`() {
        //given
        `when`<Any>(commentRepository.save(ArgumentMatchers.any())).thenReturn(testComment)

        //when
        commentService.create(createCommentRequestDto)

        //then
        verify(commentRepository, times(1)).save(any())

    }

    @Test
    fun `댓글이 가져와야 한다`() {
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