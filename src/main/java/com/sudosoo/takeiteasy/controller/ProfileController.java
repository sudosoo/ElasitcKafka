package com.sudosoo.takeiteasy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @GetMapping("/check")
    public String checkServerStatus(){
        return "check";
    }

    @GetMapping("/exception")
    public ResponseEntity<Exception> exceptionServerStatus() {
        try {
            throw new IllegalArgumentException("Exception test");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Exception(e.getMessage()));
        }
    }
}