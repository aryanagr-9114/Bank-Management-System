package com.aryan.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

// @Component tells Spring: "Load this scanner into memory when the app starts"
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Look for the wristband in the HTTP Header (it's always called
        // "Authorization")
        String authHeader = request.getHeader("Authorization");

        // 2. If no wristband, let them pass. The Bouncer (SecurityConfig) will block
        // them if it's a private room anyway.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. Rip the word "Bearer " off the front to get the raw math string
            String jwt = authHeader.substring(7);

            // 4. Decode the Math String back into English (We cheat by using a built-in
            // JJWT library parser, I'll explain later!)
            io.jsonwebtoken.Claims claims = io.jsonwebtoken.Jwts.parser()
                    .setSigningKey("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970")
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            // 5. Tell the Bank exactly who this is for the rest of the request!
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                        new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // If the math signature is fake, ignore it.
        }

        // Send them to the next line in the bank!
        filterChain.doFilter(request, response);
    }
}
