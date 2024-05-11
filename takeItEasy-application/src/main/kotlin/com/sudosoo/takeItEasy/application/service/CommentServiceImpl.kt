package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postService: PostService
) : CommentService, JpaService<Comment, Long>,JpaSpecificService<Comment, Long> {
    override var jpaRepository: BaseRepository<Comment, Long> = commentRepository
    override val jpaSpecRepository: BaseRepository<Comment, Long> = commentRepository

    override fun create(createCommentRequestDto: CreateCommentRequestDto): Comment {
        val memberId: Long = createCommentRequestDto.memberId
        val post: Post = postService.getByPostId(createCommentRequestDto.postId)
        val comment = Comment(createCommentRequestDto.content)
        comment.post = post
        comment.memberId = memberId

        return save(comment)
    }

    override fun getByCommentId(commentId: Long): Comment {
        return findById(commentId)
    }

    override fun getCommentPaginationByPostId(postId: Long, pageRequest: Pageable): Page<Comment> {
        return commentRepository.findCommentsByPostId(postId, pageRequest)
    }


}
