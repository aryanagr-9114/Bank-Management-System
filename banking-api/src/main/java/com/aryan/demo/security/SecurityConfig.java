package com.aryan.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
            // 1. We are a REST API, not a browser website, so we disable CSRF protection
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Set the rules for the Bouncer
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // Allow anyone to open the Database viewer
                .requestMatchers("/api/auth/**").permitAll()   // Allow anyone to hit Login / Register
                .anyRequest().authenticated()                  // But BLOCK every other URL unless they have a wristband
            )
            
            // 3. Just a small setting to allow the H2 visual console to render in Chrome
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // 4. GIVE THE SCANNER TO THE BOUNCER
            .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
