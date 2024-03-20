package com.sudosoo.takeItEasy.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.sudosoo.takeItEasy"])
class TakeItEasyApplication
    fun main(args: Array<String>) {
        runApplication<TakeItEasyApplication>(*args)
    }
