package com.sudosoo.takeItEasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages={"com.sudosoo.takeItEasy","com.sudosoo.takeItEasy-domain"})
class TakeItEasySupport
    fun main(args: Array<String>) {
        runApplication<TakeItEasySupport>(*args)
    }
