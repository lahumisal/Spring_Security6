package com.example.Spring_Security6.service;

import com.example.Spring_Security6.model.Users;
import com.example.Spring_Security6.model.UserPrinciple;
import com.example.Spring_Security6.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repo.findByUsername(username);
        log.info("users: {}", users);
        if(users == null){
            throw new UsernameNotFoundException("Username not found");
        }else {
            return new UserPrinciple(users);
        }

    }

}
