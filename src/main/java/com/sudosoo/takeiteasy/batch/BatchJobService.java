package com.sudosoo.takeiteasy.batch;

import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchJobService {
    private final PostService postService;

    public Job createPostsJob(JobRepository jobRepository , Step createPostsStep) {
        return new JobBuilder("createPostsJob",jobRepository)
                .start(createPostsStep)
                .build();
    }

    public Step createPostsStep(JobRepository jobRepository, ItemReader<Post> reader, ItemWriter<Post> writer, ItemProcessor<Post, Post> processor) {
        return new StepBuilder("createPostsStep", jobRepository)
                .<Post, Post>chunk(10000) // 한 번에 처리할 청크 사이즈
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // 간단한 Reader, Writer, Processor 예시

    public ItemReader<Post> reader(int start, int end) {
        return new ItemReader<Post>() {
            private int count = start;

            @Override
            public Post read() {
                if (count < end) {
                    Post post = postService.createBatchPosts(count);
                    count++;
                    return post;
                } else {
                    return null;
                }
            }
        };
    }

    public ItemWriter<Post> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Post>()
                .dataSource(dataSource)
                .sql("INSERT INTO post (title, content, category_id, member_id, view_count) VALUES (:title, :content, :categoryId, :memberId, :viewCount)")
                .beanMapped()
                .assertUpdates(false) // addBatch를 사용할 때 assertUpdates를 false로 설정
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>()) // 추가
                .build();
    }


}
