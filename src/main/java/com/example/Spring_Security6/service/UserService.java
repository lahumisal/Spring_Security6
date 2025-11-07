package com.example.Spring_Security6.service;

import com.example.Spring_Security6.model.Users;
import com.example.Spring_Security6.model.enums.Roles;
import com.example.Spring_Security6.model.response.LoginResponse;
import com.example.Spring_Security6.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService  jwtService;

    @Autowired
    public AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));

        // Set default role to STUDENT only if role is not provided
        if (user.getRole() == null) {
            user.setRole(Roles.STUDENT);
        }

        return userRepo.save(user);
    }

    public LoginResponse verifyUser(Users user) {
        log.info("verifying the user");
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            log.info("Authentication result: {}", authentication.getPrincipal());
            
            // Get user from database to retrieve full user details
            Users dbUser = userRepo.findByUsername(user.getUsername());
            
            // Generate JWT token
            String token = jwtService.generateToken(user.getUsername());
            
            // Create and return login response with username, password, and token
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(dbUser.getUsername());
            loginResponse.setPassword(dbUser.getPassword()); // Hashed password from database
            loginResponse.setToken(token);
            
            return loginResponse;
        }
        return null; // Return null if authentication fails
    }
    
    public String logout(String token) {
        if (token != null && !token.isEmpty()) {
            try {
                jwtService.blacklistToken(token);
                log.info("Token blacklisted successfully");
                return "Logout successfully";
            } catch (Exception e) {
                log.error("Error during logout: {}", e.getMessage());
                return "Logout not successfully";
            }
        }
        return "Logout not successfully";
    }
}
