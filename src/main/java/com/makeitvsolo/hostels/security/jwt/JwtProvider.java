package com.makeitvsolo.hostels.security.jwt;

import org.springframework.stereotype.Service;

@Service
public final class JwtProvider {

    private String secretKey;
    private Long expirationInMinutes;

    public String encode(String subject) {
        return "";
    }
}
