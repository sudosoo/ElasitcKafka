package com.sudosoo.takeiteasy.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudosoo.takeiteasy.dto.post.PostResponseDto;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RedisServiceImpl implements RedisService {
    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;
    public static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<PostResponseDto> findByPaginationPost(PageRequest pageable) {
        int pageNumber = pageable.getPageNumber(); // 현재 페이지 번호
        int pageSize = pageable.getPageSize(); // 페이지 크기
        int start = pageNumber * pageSize; // 시작 인덱스
        int end = start + pageSize - 1; // 끝 인덱스

        List<String> jsonValues = redisTemplate.opsForList().range("PostResponseDto",start,end);
        List<PostResponseDto> result = new ArrayList<>();
        for (String responseDto : jsonValues) {
            try{
            PostResponseDto post = objectMapper.readValue(responseDto,PostResponseDto.class);
            result.add(post);
            }catch (IOException e ){
                e.getStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<PostResponseDto> getV(String k) {
        List<String> jsonValues = redisTemplate.opsForList().range("PostResponseDto",0,-1);
        List<PostResponseDto> result = new ArrayList<>();
        for (String responseDto : jsonValues) {
            try{
                PostResponseDto post = objectMapper.readValue(responseDto,PostResponseDto.class);
                result.add(post);
            }catch (IOException e ){
                e.getStackTrace();
            }
        }
        return result;
    }



    @Override
    public void addToRedis() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            try{
            PostResponseDto postInstance = post.toResponseDto();
            String jsonPost = objectMapper.writeValueAsString(postInstance);
            redisTemplate.opsForList().leftPush("PostResponseDto",jsonPost);

            }catch (JsonProcessingException e ){
                e.getStackTrace();
            }
        }
    }

}
