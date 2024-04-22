package com.sudosoo.takeItEasy

import com.sudosoo.takeItEasy.domain.repository.PostElasticRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication

class TakeItEasyApplication
    fun main(args: Array<String>) {
        runApplication<TakeItEasyApplication>(*args)
    }
