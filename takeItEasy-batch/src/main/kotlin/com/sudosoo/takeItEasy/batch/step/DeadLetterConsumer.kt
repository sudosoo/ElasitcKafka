package com.sudosoo.takeItEasy.batch.step

import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.Post
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

@Configuration
class DeadLetterConsumer(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val kafkaProducer: KafkaProducer,
) : StepService<Event> {

    companion object {
        const val JOB_NAME = "DEAD_LETTER"
        const val CHUNK_SIZE: Int = 10000
    }

    @Bean(name = [JOB_NAME + "_step"])
    override fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .chunk<Event, Event>(CHUNK_SIZE, transactionManager)
            .reader(reader(null))
            .writer(bulkWriter())
            .startLimit(2)
            .build()
    }

    @Bean(name = [JOB_NAME + "_reader"])
    @StepScope
    override fun reader(@Value("#{jobParameters[date]}") date: String?): JpaPagingItemReader<Event> {
            return JpaPagingItemReaderBuilder<Event>()
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT e FROM event e;")
                .saveState(false)
                .build()
        }

    @Bean(name = [JOB_NAME + "_writer"])
    override fun bulkWriter(): ItemWriter<Event> {
        return ItemWriter<Event> { items ->
            items.map { event ->
                kafkaProducer.send(event)
            }

        }
    }



}
