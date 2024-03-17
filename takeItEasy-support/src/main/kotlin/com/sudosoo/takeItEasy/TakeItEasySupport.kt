package com.sudosoo.takeItEasy

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableBatchProcessing
@EnableTransactionManagement
@EnableAspectJAutoProxy
class TakeItEasySupport
    fun main(args: Array<String>) {
        runApplication<TakeItEasySupport>(*args)
    }
