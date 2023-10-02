package com.makeitvsolo.hostels.controller;

import com.makeitvsolo.hostels.dto.HostelDto;
import com.makeitvsolo.hostels.dto.HostelItemDto;
import com.makeitvsolo.hostels.security.MemberPrincipal;
import com.makeitvsolo.hostels.service.HostelService;
import com.makeitvsolo.hostels.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hostels")
public class HostelController {

    private final HostelService hostelService;
    private final TenantService tenantService;

    public HostelController(HostelService hostelService, TenantService tenantService) {
        this.hostelService = hostelService;
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<HostelItemDto>> getAll(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.status(HttpStatus.OK)
                       .body(hostelService.getAll(principal.getId()));
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody String name,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        hostelService.create(principal.getId(), name);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .build();
    }

    @GetMapping("/{hostelId}/tenants")
    public ResponseEntity<HostelDto> getAllTenants(
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                       .body(tenantService.getAllTenants(principal.getId(), hostelId));
    }

    @PostMapping("/{hostelId}/tenants")
    public ResponseEntity<?> addTenant(
            @RequestBody String memberName,
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        tenantService.addTenant(principal.getId(), hostelId, memberName);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                       .build();
    }

    @DeleteMapping("/{hostelId}/tenants")
    public ResponseEntity<?> removeTenant(
            @RequestBody Long tenantId,
            @PathVariable("hostelId") Long hostelId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        tenantService.removeTenant(principal.getId(), hostelId, tenantId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                       .build();
    }
}
