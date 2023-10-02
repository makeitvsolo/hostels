package com.makeitvsolo.hostels.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.makeitvsolo.hostels.service.security.JwtProvider;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public final class BaseJwtProvider implements JwtProvider {

    private String secretKey;
    private Long expirationInMinutes;

    public String encode(String subject) {
        var now = Instant.now();

        return JWT.create()
                       .withSubject(subject)
                       .withIssuedAt(now)
                       .withExpiresAt(now.plus(Duration.of(expirationInMinutes, ChronoUnit.MINUTES)))
                       .sign(Algorithm.HMAC256(secretKey));
    }

    public Optional<String> decodeSubject(String token) {
        try {
            var decodedJwt = JWT.require(Algorithm.HMAC256(secretKey))
                                     .build()
                                     .verify(token);

            return Optional.of(decodedJwt.getSubject());
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
