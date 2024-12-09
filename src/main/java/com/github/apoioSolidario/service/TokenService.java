package com.github.apoioSolidario.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.apoioSolidario.enums.UserRole;
import com.github.apoioSolidario.exception.GenerationTokenException;
import com.github.apoioSolidario.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {
    private final String secret = "0123456789-0123456789-0123456789";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private final String issue = "apoio-solidario-auth";

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(issue)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getUserId().toString())
                    .withClaim("Role", user.getRole().toString())
                    .withExpiresAt(getExpirationData())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new GenerationTokenException("Erro ao gerar token jwt " + ex.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(issue)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    private Instant getExpirationData() {
        return LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.of("-03:00"));
    }

    public User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (User)auth.getPrincipal();
        }
        return null;
    }

    public boolean isAdmin(){
        return  this.getPrincipal().getRole() == UserRole.ADMIN;
    }
}
