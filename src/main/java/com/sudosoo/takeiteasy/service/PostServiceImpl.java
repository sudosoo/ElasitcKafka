package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.KafkaMemberValidateResponseDto;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.kafkaMemberValidateRequestDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final CommentRepository commentRepository;
    private final KafkaProducer kafkaProducer;
    private CompletableFuture<KafkaMemberValidateResponseDto> kafkaResponseFuture;


//    @Override
//    public Post create(CreatePostRequestDto requestDto) {
//        //TODO Member validate
//        Long memberId = requestDto.getMemberId();
//        kafkaMemberValidateRequestDto memberValidateRequestDto = new kafkaMemberValidateRequestDto(memberId);
//        kafkaProducer.produceDtoToKafka(memberValidateRequestDto);
//
//        Post post = Post.of(requestDto);
//        Category category = categoryService.getCategoryByCategoryId(requestDto.getCategoryId());
//        post.setMember(memberId);
//        post.setCategory(category);
//
//        return postRepository.save(post);
//    }


    @Override
    public CompletableFuture<Post> createAsync(CreatePostRequestDto requestDto) {
        Long memberId = requestDto.getMemberId();
        kafkaMemberValidateRequestDto memberValidateRequestDto = new kafkaMemberValidateRequestDto(memberId);

        // Kafka 메시지 송신 CompletableFuture
        CompletableFuture<Void> kafkaFuture = new CompletableFuture<>();
        // Kafka 메시지 송신
        kafkaProducer.produceDtoToKafka(memberValidateRequestDto, kafkaFuture);

        // Kafka 메시지 수신 CompletableFuture
        kafkaResponseFuture = new CompletableFuture<>();

        // Kafka 메시지 송신 후 응답이 도착한 후에 Post 객체 생성 및 반환하는 CompletableFuture
        return kafkaFuture.thenComposeAsync((Void) -> {
            // Kafka 메시지 수신 CompletableFuture가 완료될 때까지 대기
            return kafkaResponseFuture.orTimeout(10000, TimeUnit.MICROSECONDS)
                    .thenApplyAsync(responseDto -> {
                        // Post 객체 생성 및 추가 작업 수행
                        Post post = Post.of(requestDto);
                        Category category = categoryService.getCategoryByCategoryId(requestDto.getCategoryId());
                        post.setCategory(category);
                        post.setMember(responseDto.getMemberId(),responseDto.getMemberName());

                        // Post 객체를 저장하고 반환
                        return postRepository.save(post);
            });
        });
    }

    @KafkaListener(topics = "${devsoo.kafka.restapi.topic}", groupId = "${devsoo.kafka.consumer.group-id}")
    private synchronized void handleKafkaMessage(KafkaMemberValidateResponseDto responseDto) {
        // Kafka 메시지를 받아와서 처리하는 로직
        if (responseDto.getTargetMethod().equals("createPost"))
            this.kafkaResponseFuture.complete(responseDto);
    }


    @Override
    public Post getByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));
        post.incrementViewCount();

        Page<Comment> comments = commentRepository.findCommentsByPostId(postId,pageRequest);
        //TODO MemberSetting 각 코멘트의 유저 이름을 찾아와서 넣어주기
        List<CommentResponseDto> responseCommentDtos = comments.stream().map(Comment::toResponseDto).toList();
        return post.toDetailDto(new PageImpl<>(responseCommentDtos));
    }

    @Override
    public List<PostTitleOnlyResponseDto> getPaginationPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::toTitleOnlyDto).toList();
    }

    @Override
    public Post createBatchPosts(int count){
        return Post.builder()
                .title("Title"+count)
                .content("content"+count)
                .memberId(1L)
                .build();
    }
}