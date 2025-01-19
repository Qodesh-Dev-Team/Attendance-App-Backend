package com.qcc.hallow.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, World!";
    }
}

