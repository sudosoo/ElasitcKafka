package com.sudosoo.takeItEasy.batch.step

import com.sudosoo.takeItEasy.application.service.order.OrdersService
import com.sudosoo.takeItEasy.domain.entity.Orders
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

@Configuration
class HeavyCreateOrders(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val dataSource: DataSource,
    private val service: OrdersService,
){
    companion object {
        const val JOB_NAME = "ORDER_TEST_DUMMY_CREATOR"
        const val CHUNK_SIZE: Int = 10000
        const val TOTAL_ITEMS: Int = 100000
    }

    @Bean(name = [JOB_NAME+"_step"])
    fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .tasklet(dataCreationTasklet(),transactionManager)
            .build()
    }

    @Bean
    fun dataCreationTasklet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            var con: Connection? = null
            var pstmt: PreparedStatement? = null
            try {
                con = dataSource.connection ?: throw SQLException("Connection is null")
                con.autoCommit = false
                val sql = "INSERT INTO orders (orderer, shipping_address, shipping_memo) VALUES (?, ?, ?)"
                pstmt = con.prepareStatement(sql)
                for (i in 1..TOTAL_ITEMS) {
                    val order = service.createBatchOrders(i) // 데이터 생성
                    pstmt.setString(1, order.orderer)
                    pstmt.setString(2, order.shippingAddress)
                    pstmt.setString(3, order.shippingMemo)
                    pstmt.addBatch()

                    // 청크 단위로 배치 실행
                    if (i % CHUNK_SIZE == 0) {
                        pstmt.executeBatch()
                        con.commit()
                        pstmt.clearParameters()
                    }
                }
                // 마지막 청크 처리
                pstmt.executeBatch()
                con.commit()
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    con?.rollback()
                } catch (rollbackException: SQLException) {
                    rollbackException.printStackTrace()
                }
            } finally {
                pstmt?.close()
                con?.close()
            }
            RepeatStatus.FINISHED
        }
    }

}
