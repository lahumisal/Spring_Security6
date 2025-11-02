package com.example.Spring_Security6.service;

import com.example.Spring_Security6.model.Users;
import com.example.Spring_Security6.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(user);
    }
}
