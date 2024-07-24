package com.sudosoo.takeItEasy.batch.job

import com.sudosoo.takeItEasy.batch.step.AdaptStep
import com.sudosoo.takeItEasy.batch.step.BatchType
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val adaptStep: AdaptStep
) {
    fun heavyCreatePostJob(): Job {
        return JobBuilder(BatchType.HEAVY_CREATE_POST.name,jobRepository)
            .start(adaptStep.getStep(BatchType.HEAVY_CREATE_POST))
            .build()
    }
    fun oldPostsDeleteJob(): Job {
        return JobBuilder(BatchType.HEAVY_CREATE_POST.name,jobRepository)
            .start(adaptStep.getStep(BatchType.HEAVY_CREATE_POST))
            .build()
    }

    fun deadLetterJob(): Job {
        return JobBuilder(BatchType.DEAD_LETTER.name,jobRepository)
            .start(adaptStep.getStep(BatchType.DEAD_LETTER))
            .build()
    }

}
