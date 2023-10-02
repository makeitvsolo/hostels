package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.dto.AccessTokenDto;
import com.makeitvsolo.hostels.dto.MemberDto;
import com.makeitvsolo.hostels.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/access")
public class AccessController {

    private final AuthenticationService authenticationService;

    public AccessController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody MemberDto payload) {
        authenticationService.register(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenDto> signIn(@RequestBody MemberDto payload) {
        var token = authenticationService.authenticate(payload);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(token);
    }
}
