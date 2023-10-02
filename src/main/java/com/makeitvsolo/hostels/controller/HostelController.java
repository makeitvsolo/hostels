package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.dto.HostelItemDto;
import com.makeitvsolo.hostels.security.MemberPrincipal;
import com.makeitvsolo.hostels.service.HostelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hostels")
public class HostelController {

    private final HostelService service;

    public HostelController(HostelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HostelItemDto>> getAll(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.status(HttpStatus.OK)
                       .body(service.getAll(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody String name,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        service.create(principal.getId(), name);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .build();
    }
}
