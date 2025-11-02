package com.example.Spring_Security6.controller;

import com.example.Spring_Security6.model.Users;
import com.example.Spring_Security6.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verifyUser(user);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String result = userService.logout(token);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Authorization header missing or invalid");
    }
}
