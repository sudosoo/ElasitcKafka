package com.sudosoo.takeItEasy.domain.repository.post;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {
    private final JPAQueryFactory queryFactory;


    public Page
}
