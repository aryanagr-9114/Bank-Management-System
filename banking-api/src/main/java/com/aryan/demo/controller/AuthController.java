package com.aryan.demo.controller;

import com.aryan.demo.dto.AuthRequest;
import com.aryan.demo.entity.User;
import com.aryan.demo.repository.UserRepository;
import com.aryan.demo.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Spring automatically injects these for us!
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // This handles: POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        // 1. Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // 2. Create the user
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        
        // 3. Hash the password before saving!
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("CUSTOMER"); // Default role

        // 4. Save to Database
        userRepository.save(newUser);

        // 5. Generate their VIP Wristband (JWT)
        String token = jwtService.generateToken(newUser.getUsername());
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // This handles: POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1. Find the user
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body("User not found!");
        }

        User user = optionalUser.get();

        // 2. Verify the hashed passwords match
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password!");
        }

        // 3. Success! Generate their Wristband.
        String token = jwtService.generateToken(user.getUsername());
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
