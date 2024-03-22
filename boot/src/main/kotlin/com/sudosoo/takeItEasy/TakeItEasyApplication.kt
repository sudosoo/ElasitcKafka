package com.sudosoo.takeItEasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EntityScan(basePackages = ["com.sudosoo.takeItEasy.domain"])
@EnableJpaRepositories("com.sudosoo.takeItEasy.domain")
@SpringBootApplication(scanBasePackages = ["com.sudosoo.takeItEasy.domain","com.sudosoo.takeItEasy.application", "com.sudosoo.takeItEasy.presentation"])
class TakeItEasyApplication
    fun main(args: Array<String>) {
        runApplication<TakeItEasyApplication>(*args)
    }
