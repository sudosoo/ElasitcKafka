package com.sudosoo.takeItEasy.application.service.comment

import com.sudosoo.takeItEasy.application.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.comment.CreateCommentRequestDto
import com.sudosoo.takeItEasy.application.service.post.PostService
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.repository.comment.CommentRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional



class CommentService(
    private val commentRepository: CommentRepository,
    private val postService: PostService
): JpaService<Comment, Long>, JpaSpecificService<Comment, Long> {

    override var jpaRepository: BaseRepository<Comment, Long> = commentRepository
    override val jpaSpecRepository: BaseRepository<Comment, Long> = commentRepository

    fun create(createCommentRequestDto: CreateCommentRequestDto): Comment {
        val memberId = createCommentRequestDto.memberId
        val post = postService.getByPostId(createCommentRequestDto.postId)
        val comment = Comment(createCommentRequestDto.content)
        comment.post = post
        comment.memberId = memberId

        return save(comment)
    }

    fun getByCommentId(commentId: Long): Comment {
        return findById(commentId)
    }

    fun getCommentPaginationByPostId(postId: Long, pageRequest: Pageable): Page<Comment> {
        return commentRepository.findCommentsByPostId(postId, pageRequest)
    }


}
