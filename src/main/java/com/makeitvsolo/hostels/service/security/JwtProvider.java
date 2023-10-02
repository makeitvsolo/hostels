package com.makeitvsolo.hostels.service.security;

import java.util.Optional;

public interface JwtProvider {

    String encode(String subject);
    Optional<String> decodeSubject(String token);
}
