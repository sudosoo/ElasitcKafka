package com.sudosoo.takeiteasy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @PostMapping(value = "/check" , name = "API Check")
    public String checkServerStatus(){
        return "check";
    }

    @PostMapping(value = "/exception" , name = "API Exception test")
    public IllegalArgumentException exceptionServerStatus() {
        return new IllegalArgumentException("Exception test");
    }
}