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
import org.springframework.transaction.PlatformTransactionManager
import java.sql.SQLException
import javax.sql.DataSource

@Configuration
class HeavyCreatePost(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val dataSource: DataSource,
    private val postService: PostService,
    private val entityManagerFactory: EntityManagerFactory
) : StepService<Post> {

    companion object {
        const val JOB_NAME = "heavyCreatePosts"
        const val CHUNK_SIZE: Int = 10000
    }

    @Bean(name = [JOB_NAME + "_step"])
    override fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .chunk<Post, Post>(CHUNK_SIZE, transactionManager)
            .reader(reader(null))
            .writer(bulkWriter())
            .build()
    }

    @Bean(name = [JOB_NAME + "_reader"])
    @StepScope
    override fun reader(@Value("#{jobParameters[date]}") date: String?): JpaPagingItemReader<Post> {
            return JpaPagingItemReaderBuilder<Post>()
                .entityManagerFactory(entityManagerFactory)
                .saveState(false)
                .build()
        }

    @Bean(name = [JOB_NAME + "_writer"])
    override fun bulkWriter(): ItemWriter<Post> {
        var count = 0
        return ItemWriter<Post> { items ->
            val con = dataSource.connection ?: throw SQLException("Connection is null")
            val sql = "INSERT INTO post (title, content, category_id, member_id, writer_name, view_count, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?)"
            val pstmt = con.prepareStatement(sql)
            try {
                items.chunked(CHUNK_SIZE).forEach{
                    con.autoCommit = false
                    val post = postService.createBatchPosts(count)

                    pstmt.setString(1, post.title)
                    pstmt.setString(2, post.content)
                    pstmt.setLong(3, 1L)
                    pstmt.setLong(4, post.memberId)
                    pstmt.setString(5, post.writer)
                    pstmt.setInt(6, post.viewCount)
                    pstmt.setBoolean(7, post.isDeleted)

                    pstmt.addBatch()
                    count++
                }
                pstmt.executeBatch()
                con.commit()
                pstmt.clearParameters()
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
