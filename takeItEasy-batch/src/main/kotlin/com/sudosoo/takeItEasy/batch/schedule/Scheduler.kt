package com.sudosoo.takeItEasy.batch.schedule

import com.sudosoo.takeItEasy.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class Scheduler(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher) {
    @Scheduled(cron = "0 0 0 * * ?")
        fun run(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.heavyCreatePostJob(), jobParameter)
    }

    fun testRun(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
    }
}