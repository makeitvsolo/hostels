package com.makeitvsolo.hostels.security.jwt;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class JwtProvider {

    private String secretKey;
    private Long expirationInMinutes;

    public String encode(String subject) {
        return "";
    }

    public Optional<String> decodeSubject(String token) {
        return Optional.empty();
    }
}
