package com.aryan.demo.repository;

import com.aryan.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Look at this! We just name the method exactly what we want, 
    // and Spring Boot automatically writes the SQL: 
    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}
