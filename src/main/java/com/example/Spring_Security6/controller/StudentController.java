package com.example.Spring_Security6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vi/api/student")
public class StudentController {

    @GetMapping
    public String student() {
        return "student";
    }
}
