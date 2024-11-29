package com.github.apoioSolidario.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.apoioSolidario.exception.GenerationTokenException;
import com.github.apoioSolidario.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private final String secret = "0123456789-0123456789-0123456789";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private final String issue = "apoio-solidario-auth";

    public String generateToken(User user) {
        try {
            String token = JWT.create()
                    .withIssuer(issue)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getUserId().toString())
                    .withClaim("Role", user.getRole().toString())
                    .withExpiresAt(getExpirationData())
                    .sign(algorithm);
            return token;
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

}
