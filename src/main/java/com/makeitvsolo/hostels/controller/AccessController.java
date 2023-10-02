package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.controller.response.ErrorMessageDto;
import com.makeitvsolo.hostels.service.AuthenticationService;
import com.makeitvsolo.hostels.service.dto.member.MemberDto;
import com.makeitvsolo.hostels.service.exception.member.MemberAlreadyExistsException;
import com.makeitvsolo.hostels.service.exception.member.MemberNotFoundException;
import com.makeitvsolo.hostels.service.exception.member.PasswordsAreNotMatchesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/access")
public class AccessController {

    private final AuthenticationService authenticationService;

    public AccessController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody MemberDto payload) {
        try {
            authenticationService.register(payload);

            log.info("Member registered");
            return ResponseEntity.status(HttpStatus.CREATED)
                           .build();
        } catch (MemberAlreadyExistsException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody MemberDto payload) {
        try {
            var token = authenticationService.authenticate(payload);

            log.info("Member authenticated");
            return ResponseEntity.status(HttpStatus.OK)
                           .body(token);
        } catch (MemberNotFoundException | PasswordsAreNotMatchesException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                           .body(new ErrorMessageDto(ex.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build();
        }
    }
}
