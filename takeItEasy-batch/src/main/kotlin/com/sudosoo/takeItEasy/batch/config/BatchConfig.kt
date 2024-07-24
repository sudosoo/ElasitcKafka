package com.sudosoo.takeItEasy.batch.config

import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.boot.autoconfigure.batch.BatchProperties
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration
@EnableConfigurationProperties(BatchProperties::class)
class BatchConfig {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.batch.job", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    fun jobLauncherApplicationRunner(
        jobLauncher: JobLauncher?,
        jobExplorer: JobExplorer?,
        jobRepository: JobRepository?,
        properties: BatchProperties
    ): JobLauncherApplicationRunner {
        val runner = JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository)
        val jobNames = properties.job.name
        if (StringUtils.hasText(jobNames)) {
            runner.setJobName(jobNames)
        }
        return runner
    }
}