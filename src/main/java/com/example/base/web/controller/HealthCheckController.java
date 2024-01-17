package com.example.base.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health-check")
@RestController
public class HealthCheckController {

    @GetMapping
    public void healthCheck() {
        //do nothing
    }

    @GetMapping("/success")
    public String healthCheckSuccess() {
        return "OK";
    }

    @GetMapping("/exception")
    public void healthCheckException() throws IllegalArgumentException{
        throw new IllegalArgumentException("잘못된 요청입니다. 요청내용을 확인하세요.");
    }
}