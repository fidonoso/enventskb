package com.events.kibernum.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

    private final Key secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("La clave secreta JWT no puede ser nula o vacía");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
    

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(secretKey)
                .compact();
    }

    public Key getSecretKey() {
        return secretKey;
    }

    public Claims validateToken(String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

