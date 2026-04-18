package com.aryan.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // This is the highly secured signature key used to sign the wristbands
    private static final String SECRET_KEY_BASE_64 = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    /**
     * Creates a new token (wristband) for the given username.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                // JWT Expires in exactly 10 hours
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE_64);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
