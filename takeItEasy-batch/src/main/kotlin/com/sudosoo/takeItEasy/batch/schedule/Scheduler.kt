package com.sudosoo.takeItEasy.batch.schedule

import com.sudosoo.takeItEasy.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Configuration
class Scheduler(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher) {
    @Scheduled(cron = "0 0 0 * * ?")
        fun run(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.heavyCreatePostJob(), jobParameter)
        jobLauncher.run(batchJob.oldPostsDeleteJob(), jobParameter)
    }

    @Scheduled(fixedRate = 10000)
    fun runDeadLetterJob(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.deadLetterJob(), jobParameter)
    }

}