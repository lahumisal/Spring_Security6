package com.example.Spring_Security6.config;

import com.example.Spring_Security6.service.JwtService;
import com.example.Spring_Security6.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        String token= null;
        String username=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token= authHeader.substring(7);
            
            // Check if token is blacklisted before processing
            if(jwtService.isTokenBlacklisted(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Bellow i get username from token
            username = jwtService.extractUserNameFromToken(token);
        }

        // i check user is not authenticate
        if (username!=null &&  SecurityContextHolder.getContext().getAuthentication()==null) {

            // here i check the user is part of database or not and it will give entire userDetail object
            UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails )){
                // bellow token as for principle, creadentials and authorities
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                // Now your auth token ready and now set it in contextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
    filterChain.doFilter(request, response);
    }
}
