package com.sudosoo.takeItEasy.batch.schedule

import com.sudosoo.takeItEasy.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Configuration

@Configuration
class StartupTasks(
    val scheduler: Scheduler,
    val jobLauncher: JobLauncher,
    val batchJob: BatchJob
) :ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val jobParameters = JobParametersBuilder()
            .addLong("startTime", System.currentTimeMillis())
            .toJobParameters()
        jobLauncher.run(batchJob.orderTestDummyCreator(), jobParameters)
    }
}
