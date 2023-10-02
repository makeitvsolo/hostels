package com.makeitvsolo.hostels.service.security;

public interface JwtProvider {

    String encode(String subject);
    String decodeSubject(String token);
}
