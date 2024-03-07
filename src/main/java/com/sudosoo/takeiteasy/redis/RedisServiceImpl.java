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
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class RedisServiceImpl implements RedisService {
    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;
    public static ObjectMapper objectMapper = new ObjectMapper();

    //pageable 객체로 key 값에 해당 하는 밸류를 page갯수만큼 불러온다.
    @Override
    public <T> List<T> findByPaginationPost(String responseDtoName, PageRequest pageable) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(responseDtoName);

        }catch (ClassNotFoundException e){
            e.getStackTrace();
        }
        int pageNumber = pageable.getPageNumber(); // 현재 페이지 번호
        int pageSize = pageable.getPageSize(); // 페이지 크기
        int start = pageNumber * pageSize; // 시작 인덱스
        int end = start + pageSize - 1; // 끝 인덱스

        List<String> jsonValues = redisTemplate.opsForList().range(responseDtoName, start, end);
        List<T> result = new ArrayList<>();
        for (String responseDto : jsonValues) {
            try {
                T value = (T) objectMapper.readValue(responseDto, clazz);
                result.add(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    //메소드 이름을 key값으로 해당 하는 밸류들 불러온다
    @Override
    public <T> List<T> getValues(String className) {
        List<T> result = new ArrayList<>();
        try {
            // 패키지명과 클래스명을 조합하여 전체 클래스 이름을 만듭니다.
            String fullClassName = "com.sudosoo.takeiteasy.dto." + className;
            Class<?> clazz = Class.forName(fullClassName);

            List<String> jsonValues = redisTemplate.opsForList().range(className, 0, -1);
            for (String responseDto : Objects.requireNonNull(jsonValues)) {
                // Reflection을 사용하여 클래스의 객체 생성
                T value = objectMapper.readValue(responseDto, (Class<T>) clazz);
                result.add(value);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void saveReadValue(Object value) {
            try{
                String className = value.getClass().getSimpleName();
                String jsonObject = objectMapper.writeValueAsString(value);
                redisTemplate.opsForList().leftPush(className,jsonObject);

            }catch (JsonProcessingException e ){
                e.getStackTrace();
            }
    }

    @Override
    public void postRepositoryRedisSynchronization() {
        redisTemplate.delete("PostResponseDto");
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
