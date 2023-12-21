package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponsetDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;


    @Override
    public Post createdPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Post post = Post.of(createPostRequestDto);
        Category category = getCategory(createPostRequestDto.getCategoryId());

        post.setMember(member);
        post.setCategory(category);

        return postRepository.save(post);
    }

    private Category getCategory(Long categoryId) {
        return categoryService.getCategoryByCategoryId(categoryId);
    }

    @Override
    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : " + postId));
    }

   @Override
   public List<PostTitleDto> getPostByCategoryId(Long categoryId, PageRequest pageRequest) {
        List<Post> posts = postRepository.findAllByCategoryId(categoryId, pageRequest);

        if (posts.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리에 등록된 게시물이 없습니다.");
        }
        return posts.stream().map(Post::toTitleOnlyDto).toList();
    }

    @Override
    public PostDetailResponsetDto getPostDetailByPostId(Long postId, PageRequest pageRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));
        List<Comment> comments = post.getComments();
        List<CommentResposeDto> responseCommentDto = comments.stream().map(Comment::toResponseDto).toList();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), responseCommentDto.size());
        Page<CommentResposeDto> paginatedComment =
                new PageImpl<>(responseCommentDto.subList(start, end), pageRequest, responseCommentDto.size());

        return post.toDetailDto(paginatedComment);
    }
}