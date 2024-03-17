package com.sudosoo.takeItEasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class TakeItEasyPresentation
fun main(args: Array<String>) {
    runApplication<TakeItEasyPresentation>(*args)
}