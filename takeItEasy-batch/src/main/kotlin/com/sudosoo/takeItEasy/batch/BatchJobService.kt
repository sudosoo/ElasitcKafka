package com.sudosoo.takeItEasy.batch

import com.sudosoo.takeItEasy.application.service.PostService
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Service
@EnableBatchProcessing
class BatchJobService(
    val postService: PostService,
    val transactionManager: PlatformTransactionManager
) {

    fun createPostsJob(jobRepository: JobRepository, createPostsStep: Step): Job {
        return JobBuilder("createPostsJob",jobRepository)
            .start(createPostsStep)
            .build()
    }


    fun createPostsStep(jobRepository: JobRepository, reader: ItemReader<Post>, writer: ItemWriter<Post>, processor: ItemProcessor<Post, Post>?): Step {
        return StepBuilder("createPostsStep",jobRepository)
            .chunk<Post, Post>(10000,transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }

    fun reader(start: Int, end: Int): ItemReader<Post> {
        var count = start

        return ItemReader<Post> {
            if (count < end) {
                val post = postService.createBatchPosts(count)
                count++
                post
            } else {
                null
            }
        }
    }

    fun writer(dataSource: DataSource): ItemWriter<Post> {
        return JdbcBatchItemWriterBuilder<Post>()
            .dataSource(dataSource)
            .sql("INSERT INTO post (title, content, category_id, member_id, view_count) VALUES (:title, :content, :categoryId, :memberId, :viewCount)")
            .beanMapped()
            .assertUpdates(false)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<Post>())
            .build()
    }
}
