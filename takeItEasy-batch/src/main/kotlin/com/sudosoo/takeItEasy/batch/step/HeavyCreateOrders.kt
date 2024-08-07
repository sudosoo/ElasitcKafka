package com.sudosoo.takeItEasy.batch.step

import com.sudosoo.takeItEasy.application.service.order.OrdersService
import com.sudosoo.takeItEasy.domain.entity.Orders
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

@Component
class HeavyCreateOrders(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val dataSource: DataSource,
    private val service: OrdersService,
    private val entityManagerFactory: EntityManagerFactory
){

    companion object {
        const val JOB_NAME = "ORDER_TEST_DUMMY_CREATOR"
        const val CHUNK_SIZE: Int = 10000
    }

    @Bean(name = [JOB_NAME+"_step"])
    fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .chunk<Orders, Orders>(CHUNK_SIZE, transactionManager)
            .reader(reader(null))
            .writer(bulkWriter())
            .startLimit(2)
            .build()
    }

    @StepScope
    //@Bean(name = [JOB_NAME + "_reader"])
    fun reader(@Value("#{jobParameters[date]}") date: String?): JpaPagingItemReader<Orders> {
            return JpaPagingItemReaderBuilder<Orders>()
                .entityManagerFactory(entityManagerFactory)
                .saveState(false)
                .build()
        }

    //@Bean(name = [JOB_NAME+"_writer"])
    fun bulkWriter(): ItemWriter<Orders> {
        var count = 0
        return ItemWriter<Orders> { items ->
            val con = dataSource.connection ?: throw SQLException("Connection is null")
            val sql = "INSERT INTO orders (orderer,shippingAddress,shippingMemo) VALUES (?, ?, ?)"
            val pstmt = con.prepareStatement(sql)
            try {
                items.chunked(CHUNK_SIZE).forEach{

                    con.autoCommit = false
                    val orders = service.createBatchOrders(count)

                    pstmt.setString(1, orders.orderer)
                    pstmt.setString(2, orders.shippingAddress)
                    pstmt.setString(3, orders.shippingMemo)

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
