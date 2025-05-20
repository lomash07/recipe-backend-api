package com.recipe.app.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public String extractUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Error validating JWT token", e);
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            return true;
        }
    }
}