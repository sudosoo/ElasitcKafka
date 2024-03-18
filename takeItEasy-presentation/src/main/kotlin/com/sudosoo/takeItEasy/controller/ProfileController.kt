package com.sudosoo.takeItEasy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProfileController {
    @GetMapping("/check", name = "API Check")
    fun checkServerStatus(): String {
        return "check"
    }

    @GetMapping("/exception", name = "API Exception test")
    fun exceptionServerStatus(): IllegalArgumentException {
        return IllegalArgumentException("Exception test")
    }
}