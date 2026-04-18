package com.aryan.demo.dto;

import lombok.Data;

@Data
public class AuthRequest {
    // When the user sends a POST request, this is the JSON structure we expect
    private String username;
    private String password;
}
