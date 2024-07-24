package com.sudosoo.takeItEasy.batch.job

import com.sudosoo.takeItEasy.batch.step.BatchType
import com.sudosoo.takeItEasy.batch.step.DeadLetterConsumer
import com.sudosoo.takeItEasy.batch.step.HeavyCreatePost
import com.sudosoo.takeItEasy.batch.step.OldPostsDelete
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchJob(
    val jobRepository: JobRepository,
    val heavyCreatePost: HeavyCreatePost,
    val oldPostsDelete: OldPostsDelete,
    val deadLetterConsumer: DeadLetterConsumer
) {
    fun heavyCreatePostJob(): Job {
        return JobBuilder(BatchType.HEAVY_CREATE_POST.name,jobRepository)
            .start(heavyCreatePost.step())
            .build()
    }
    fun oldPostsDeleteJob(): Job {
        return JobBuilder(BatchType.HEAVY_CREATE_POST.name,jobRepository)
            .start(oldPostsDelete.step())
            .build()
    }

    fun deadLetterJob(): Job {
        return JobBuilder(BatchType.DEAD_LETTER.name,jobRepository)
            .start(deadLetterConsumer.step())
            .build()
    }

}
