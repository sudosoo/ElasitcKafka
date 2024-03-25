package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.heart.CommentHeartRequestDto
import com.sudosoo.takeItEasy.application.dto.heart.PostHeartRequestDto
import com.sudosoo.takeItEasy.domain.entity.Comment
import com.sudosoo.takeItEasy.domain.entity.Heart
import com.sudosoo.takeItEasy.domain.entity.Post
import com.sudosoo.takeItEasy.domain.repository.HeartRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class HeartServiceImpl(
    val heartRepository: HeartRepository,
    val postService: PostService,
    val commentService: CommentService
) : HeartService , JpaService<Heart, Long> {
    override var jpaRepository: JpaRepository<Heart,Long> = heartRepository

    override fun createPostHeart(requestDto: PostHeartRequestDto): Heart {
        //TODO MemberSetting
        val memberId: Long = requestDto.memberId
        val post: Post = postService.getByPostId(requestDto.postId)

        require(!heartRepository.existsByMemberIdAndPost(memberId, post)) { "Duplicated Like !" }
        val heart = getPostHeart(memberId,post)

        return save(heart)
    }

    override fun createCommentHeart(requestDto: CommentHeartRequestDto): Heart {
        //TODO MemberSetting
        val memberId: Long = requestDto.memberId
        val comment = commentService.getByCommentId(requestDto.commentId)

        require(!heartRepository.existsByMemberIdAndComment(memberId, comment)) { "Duplicated Like !" }
        val heart = getCommentHeart(memberId,comment)

        return save(heart)
    }


    override fun postDisHeart(requestDto: PostHeartRequestDto) {
        //TODO MemberSetting
        val memberId: Long = requestDto.memberId
        val post: Post = postService.getByPostId(requestDto.postId)
        val heart: Heart = findHeartByMemberAndPostOrComment(memberId, post)

        heart.unHeartPost()

        deleteById(heart.id)
    }

    override fun commentDisHeart(requestDto: CommentHeartRequestDto) {
        //TODO MemberSetting
        val memberId: Long = requestDto.memberId
        val comment = commentService.getByCommentId(requestDto.commentId)
        val heart: Heart = findHeartByMemberAndPostOrComment(memberId, comment)

        heart.unHeartComment()

        deleteById(heart.id)
    }

    private fun findHeartByMemberAndPostOrComment(memberId: Long, reference: Any): Heart {
        return when (reference) {
            is Post -> {
                getPostHeart(memberId, reference)
            }
            is Comment -> {
                getCommentHeart(memberId, reference)
            }
            else -> {
                throw IllegalArgumentException("Unsupported reference type")
            }
        }
    }

    private fun getPostHeart(memberId: Long, p: Post): Heart {
        return heartRepository.findByMemberIdAndPost(memberId, p)
            .orElseThrow{ IllegalArgumentException("Could not find PostHeart id") }
    }

    private fun getCommentHeart(memberId: Long, c: Comment): Heart {
        return heartRepository.findByMemberIdAndComment(memberId, c)
            .orElseThrow{ IllegalArgumentException("Could not find CommentHeart id") }
    }
}
