package com.sudosoo.takeItEasy.batch.step

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
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
class OldPostsDelete(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val dataSource: DataSource,
    private val entityManagerFactory: EntityManagerFactory
) : StepService<Post> {

    companion object {
        const val JOB_NAME = "oldPostsDelete"
        const val CHUNK_SIZE: Int = 10000
    }

    @Bean(name = [JOB_NAME + "_step"])
    override fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .chunk<Post, Post>(CHUNK_SIZE, transactionManager)
            .reader(reader(null))
            .writer(bulkWriter())
            .faultTolerant()
            .retryLimit(2)
            .retry(Exception::class.java)
            .build()
    }

    @Bean(name = [JOB_NAME + "_reader"])
    @StepScope
    override fun reader(@Value("#{jobParameters[date]}") date: String?): JpaPagingItemReader<Post> {
        val cutoffDay = LocalDate.parse(date).minusDays(90)
        return JpaPagingItemReaderBuilder<Post>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT p.id FROM Post p WHERE is_deleted = true AND deleted_at < :cutoffDay")
            .parameterValues(mapOf("cutoffDay" to cutoffDay))
            .saveState(false)
            .build()
    }

    @Bean(name = [JOB_NAME + "_writer"])
    override fun bulkWriter(): ItemWriter<Post> {
        return ItemWriter<Post> { items ->
            val con = dataSource.connection ?: throw SQLException("Connection is null")
            val sql = "DELETE FROM Post WHERE id = ?;"
            val pstmt = con.prepareStatement(sql)
            con.autoCommit = false
            try {
                items.chunked(CHUNK_SIZE).forEach{
                    for (chunk in it) {
                        pstmt.setLong(1, chunk.id)
                        pstmt.addBatch()
                    }
                    pstmt.executeBatch()
                    con.commit()
                    pstmt.clearParameters()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    con.rollback()
                } catch (sqlException: SQLException) {
                    sqlException.printStackTrace()
                }
            } finally {
                pstmt.close()
                con.close()
            }
        }
    }



}
