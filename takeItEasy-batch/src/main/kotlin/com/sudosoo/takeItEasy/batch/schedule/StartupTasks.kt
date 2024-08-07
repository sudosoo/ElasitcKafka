package com.sudosoo.takeItEasy.batch.schedule

import com.sudosoo.takeItEasy.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.time.LocalDate

@Configuration
class StartupTasks(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher
) :ApplicationRunner {
    override fun run(rgs: ApplicationArguments?) {
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.orderTestDummyCreator(), jobParameter)
    }
}
