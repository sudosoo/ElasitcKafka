package com.sudosoo.takeItEasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableScheduling
class TakeItEasyApplication
    fun main(args: Array<String>) {
        runApplication<TakeItEasyApplication>(*args)
    }
