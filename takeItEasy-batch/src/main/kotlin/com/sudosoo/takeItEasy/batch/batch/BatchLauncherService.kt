package com.sudosoo.takeItEasy.batch.batch

import com.sudosoo.takeItEasy.application.service.PostService
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

class BatchLauncherService(
    private val jobLauncher: JobLauncher,
    private val jobRepository: JobRepository,
    private val batchJobService: BatchJobService,
    private val dataSource: DataSource,
    private val postService: PostService
) {
    private var con: Connection? = null
    private var pstmt: PreparedStatement? = null

    fun runBatchJob() {
        val sql = "INSERT INTO post (title, content, category_id, member_id, writer_name, view_count, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?)"

        try {
            con = dataSource.connection
            con?.autoCommit = false
            pstmt = con?.prepareStatement(sql)
            for (i in 0 until 100000) {
                val post = postService.createBatchPosts(i)

                pstmt?.setString(1, post.title)
                pstmt?.setString(2, post.content)
                pstmt?.setLong(3, 1L)
                pstmt?.setLong(4, post.memberId)
                pstmt?.setString(5, post.writerName)
                pstmt?.setInt(6, post.viewCount)
                pstmt?.setBoolean(7, post.isDeleted)

                // Add to batch
                pstmt?.addBatch()
                // Clear parameters
                pstmt?.clearParameters()

                if (i % 10000 == 0) {
                    // Execute batch
                    pstmt?.executeBatch()
                    // Clear batch
                    pstmt?.clearBatch()
                    // Commit
                    con?.commit()
                }
            }
            // Execute remaining batch
            pstmt?.executeBatch()
            con?.commit()
            pstmt?.clearBatch()
        } catch (e: Exception) {
            try {
                // Rollback on failure
                e.printStackTrace()
                con?.rollback()
            } catch (sqlException: SQLException) {
                sqlException.printStackTrace()
            }
        } finally {
            // Clean up resources
            pstmt?.close()
            con?.close()
        }
    }

    @Bean
    fun runBatchJobV2() {
        for (i in 0 until 10) {
            val start = i * 10000
            val end = (i + 1) * 10000

            val reader: ItemReader<Post> = batchJobService.reader(start, end)
            val writer: ItemWriter<Post> = batchJobService.writer(dataSource)

            val step: Step = batchJobService.createPostsStep(jobRepository, reader, writer, null)

            val job: Job = batchJobService.createPostsJob(jobRepository, step)

            val jobExecution: JobExecution = jobLauncher.run(
                job,
                JobParametersBuilder()
                    .addString("jobID", "createPostsJob$i")
                    .addLong("timestamp", System.currentTimeMillis())
                    .addString("start", start.toString())
                    .addString("end", end.toString())
                    .toJobParameters()
            )

            println("Job Status: ${jobExecution.status}")
            println("Job succeeded: ${jobExecution.exitStatus.exitCode == ExitStatus.COMPLETED.exitCode}")
        }
    }
}
