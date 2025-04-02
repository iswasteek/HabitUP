package com.fsf.habitup.Security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fsf.habitup.Service.UserService;
import com.fsf.habitup.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    @Lazy
    private UserService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("Request Path: " + path);

        if (path.startsWith("/habit/auth/")) {
            System.out.println("✅ Skipping JWT filter for path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("🔒 Processing JWT for path: " + path);

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("Extracted Token: " + token);

            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);
                System.out.println("✅ Valid Token for User: " + email);

                User userDetails = userDetailsService.findUserByEmail(email);
                System.out.println("Retrieved User from DB: " + userDetails.getEmail());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("❌ Token validation failed");
            }
        } else {
            System.out.println("❌ No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}
