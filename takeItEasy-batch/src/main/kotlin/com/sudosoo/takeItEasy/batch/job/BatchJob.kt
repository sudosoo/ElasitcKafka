package com.sudosoo.takeItEasy.batch.job

import com.sudosoo.takeItEasy.batch.step.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val heavyCreatePost: HeavyCreatePost,
    val oldPostsDelete: OldPostsDelete,
    val deadLetterConsumer: DeadLetterConsumer,
    val heavyCreateOrders: HeavyCreateOrders
){
    fun heavyCreatePostJob(): Job {
        return JobBuilder(BatchType.HEAVY_CREATE_POST.name,jobRepository)
            .start(heavyCreatePost.step())
            .build()
    }
    fun oldPostsDeleteJob(): Job {
        return JobBuilder(BatchType.OLD_POSTS_DELETE.name,jobRepository)
            .start(oldPostsDelete.step())
            .build()
    }

    fun deadLetterJob(): Job {
        return JobBuilder(BatchType.DEAD_LETTER.name,jobRepository)
            .start(deadLetterConsumer.step())
            .build()
    }

    fun orderTestDummyCreator():Job{
        return JobBuilder(BatchType.ORDER_TEST_DUMMY_CREATOR.name,jobRepository)
            .start(heavyCreateOrders.step())
            .build()
    }

}
