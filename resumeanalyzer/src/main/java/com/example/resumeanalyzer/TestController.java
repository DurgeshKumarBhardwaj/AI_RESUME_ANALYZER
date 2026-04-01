package com.example.resumeanalyzer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String testSetup() {
        return "My AI Resume Analyzer backend is running perfectly!";
    }
}