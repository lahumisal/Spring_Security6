package com.example.Spring_Security6.controller;

import com.example.Spring_Security6.model.Users;
import com.example.Spring_Security6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/home")
public class HomeController {

    @Autowired
    private UserService  userService;

    @GetMapping
    public String home() {
        return "Home controller";
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.registerUser(user);
    }
}
