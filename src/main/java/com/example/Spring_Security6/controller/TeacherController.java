package com.example.Spring_Security6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vi/api/teacher")
public class TeacherController {

    @GetMapping
    public String teacher() {
        return "teacher controller";
    }
}
